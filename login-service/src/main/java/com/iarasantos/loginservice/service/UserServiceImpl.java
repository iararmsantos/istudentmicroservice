package com.iarasantos.loginservice.service;


import com.iarasantos.loginservice.controller.UserController;
import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.data.vo.v1.UserResponse;
import com.iarasantos.loginservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.loginservice.exceptions.ResourceNotFoundException;
import com.iarasantos.loginservice.model.UserEntity;
import com.iarasantos.loginservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PagedResourcesAssembler<UserResponse> assembler;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Override
    public PagedModel<EntityModel<UserResponse>> getUsers(Pageable pageable) {
        // Fetch paginated Student entities from the repository
        Page<UserEntity> studentPage = repository.findAll(pageable);

        // Map each User to a UserResponse
        Page<UserResponse> userResponses = studentPage.map(s ->
             this.modelMapper.map(s, UserResponse.class)
        );
        // Add self-links to each StudentVO
        userResponses.forEach(p -> p.add(
                linkTo(methodOn(UserController.class)
                        .getUser(p.getUserId())
                ).withSelfRel()
        ));

        // add pagination hateoas link
        Link link = linkTo(methodOn(UserController.class).getUsers(pageable.getPageNumber(),
                pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(userResponses, link);
    }

    @Override
    public UserResponse getUser(Long userId) {
        UserEntity user = repository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + userId + " not found!"));
        UserResponse vo = this.modelMapper.map(user, UserResponse.class);

        //hateoas
        vo.add(linkTo(methodOn(UserController.class)
                .getUser(vo.getUserId())).withSelfRel());
        return vo;
    }

    @Override
    public UserResponse getByUserId(String userId) {
        UserEntity user = repository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + userId + " not found!"));
        UserResponse vo = this.modelMapper.map(user, UserResponse.class);

        //hateoas
        vo.add(linkTo(methodOn(UserController.class)
                .getUser(vo.getUserId())).withSelfRel());
        return vo;
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        UserEntity user = repository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + email + " not found!"));
        UserResponse vo = this.modelMapper.map(user, UserResponse.class);

        //hateoas
        vo.add(linkTo(methodOn(UserController.class)
                .getUser(vo.getUserId())).withSelfRel());
        return vo;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        if (request == null) {
            throw new RequiredObjectIsNullException();
        }

        UserEntity user = this.modelMapper.map(request, UserEntity.class);
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setCreationDate(new Date());
        UserEntity userEntity = repository.save(user);

        UserResponse vo = this.modelMapper.map(userEntity, UserResponse.class);

        vo.add(linkTo(methodOn(UserController.class).getUser(vo.getUserId())).withSelfRel());

        return vo;
    }

    @Override
    public void deleteUser(Long userId) {

        UserEntity user = repository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + userId + " not found.")
        );

        repository.deleteById(user.getId());
    }

    @Override
    public UserResponse updateUser(UserRequest request) {
        if (request == null) {
            throw new RequiredObjectIsNullException();
        }

        UserEntity user = repository.findById(request.getKey()).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + request.getKey() + " not found!"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        UserResponse vo = this.modelMapper.map(repository.save(user), UserResponse.class);

        //hateoas
        vo.add(linkTo(methodOn(UserController.class).getUser(vo.getUserId())).withSelfRel());

        return vo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + username + " not found!"
        ));

        return new User(user.getEmail(), user.getPassword(), true, true, true, true, new ArrayList<>());
    }
}

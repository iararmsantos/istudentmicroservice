package com.iarasantos.loginservice.service;


import com.iarasantos.loginservice.controller.UserController;
import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.data.vo.v1.UserResponse;
import com.iarasantos.loginservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.loginservice.exceptions.ResourceNotFoundException;
import com.iarasantos.loginservice.model.UserEntity;
import com.iarasantos.loginservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    private ModelMapper modelMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = new ModelMapper();
        propertyMapping();
    }

    @Override
    public List<UserResponse> getUsers() {
        List<UserEntity> usersList = repository.findAll();

        List<UserResponse> users = usersList
                .stream()
                .map((item) -> this.modelMapper.map(item, UserResponse.class))
                .collect(Collectors.toList());

        //hateoas
        users
                .stream()
                .forEach(user -> user.add(linkTo(methodOn(UserController.class)
                        .getUser(user.getUserId())).withSelfRel()));

        return users;
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
        UserResponse vo = this.modelMapper.map(repository.save(user), UserResponse.class);

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

    private void propertyMapping() {
        TypeMap<UserEntity, UserRequest> propertyMapper = this.modelMapper.createTypeMap(UserEntity.class, UserRequest.class);
        propertyMapper.addMapping(UserEntity::getId, UserRequest::setKey);
        TypeMap<UserRequest, UserEntity> propertyMapper2 = this.modelMapper.createTypeMap(UserRequest.class, UserEntity.class);
        propertyMapper2.addMapping(UserRequest::getKey, UserEntity::setId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + username + " not found!"
        ));

        return new User(user.getEmail(), user.getPassword(), true, true, true, true, new ArrayList<>());
    }
}

package com.iarasantos.loginservice.service;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.loginservice.controller.UserController;
import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.loginservice.exceptions.ResourceNotFoundException;
import com.iarasantos.loginservice.model.User;
import com.iarasantos.loginservice.repository.UserRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    private ModelMapper modelMapper;

    public UserServiceImpl() {
        this.modelMapper = new ModelMapper();
        propertyMapping();
    }

    @Override
    public List<UserRequest> getUsers() {
        List<User> usersList= repository.findAll();

        List<UserRequest> users = usersList
                .stream()
                .map((item) -> this.modelMapper.map(item, UserRequest.class))
                .collect(Collectors.toList());

        //hateoas
        users
                .stream()
                .forEach(user -> user.add(linkTo(methodOn(UserController.class)
                        .getUser(user.getKey())).withSelfRel()));

        return users;
    }

    @Override
    public UserRequest getUser(Long userId) {
        User user = repository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + userId + " not found!"));
        UserRequest vo = this.modelMapper.map(user, UserRequest.class);

        //hateoas
        vo.add(linkTo(methodOn(UserController.class)
                .getUser(vo.getKey())).withSelfRel());
        return vo;
    }

    @Override
    public UserRequest createUser(UserRequest request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }
        User user = this.modelMapper.map(request, User.class);
        user.setCreationDate(new Date());
        UserRequest vo = this.modelMapper.map(repository.save(user), UserRequest.class);

        vo.add(linkTo(methodOn(UserController.class).getUser(vo.getKey())).withSelfRel());

        return vo;
    }

    @Override
    public void deleteUser(Long userId) {

        User user = repository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + userId + " not found.")
        );

        repository.deleteById(user.getId());
    }

    @Override
    public UserRequest updateUser(UserRequest request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }

        User user = repository.findById(request.getKey()).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + request.getKey() + " not found!"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        UserRequest vo = this.modelMapper.map(repository.save(user), UserRequest.class);

        //hateoas
        vo.add(linkTo(methodOn(UserController.class).getUser(vo.getKey())).withSelfRel());

        return vo;
    }

    private void propertyMapping() {
        TypeMap<User, UserRequest> propertyMapper = this.modelMapper.createTypeMap(User.class, UserRequest.class);
        propertyMapper.addMapping(User::getId, UserRequest::setKey);
        TypeMap<UserRequest, User> propertyMapper2 = this.modelMapper.createTypeMap(UserRequest.class, User.class);
        propertyMapper2.addMapping(UserRequest::getKey, User::setId);
    }
}

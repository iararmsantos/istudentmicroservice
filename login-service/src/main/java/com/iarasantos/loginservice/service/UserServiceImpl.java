package com.iarasantos.loginservice.service;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.loginservice.controller.UserController;
import com.iarasantos.loginservice.data.vo.v1.UserVO;
import com.iarasantos.loginservice.exceptions.RequiredObjectIsNullException;
import com.iarasantos.loginservice.exceptions.ResourceNotFoundException;
import com.iarasantos.loginservice.model.User;
import com.iarasantos.loginservice.repository.UserRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<UserVO> getUsers() {
        List<UserVO> users = DozerMapper.parseListObjects(repository.findAll(), UserVO.class);

        //hateoas
        users
                .stream()
                .forEach(user -> user.add(linkTo(methodOn(UserController.class)
                        .getUser(user.getKey())).withSelfRel()));

        return users;
    }

    @Override
    public UserVO getUser(Long userId) {
        User user = repository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + userId + " not found!"));
        UserVO vo = DozerMapper.parseObject(user, UserVO.class);

        //hateoas
        vo.add(linkTo(methodOn(UserController.class)
                .getUser(vo.getKey())).withSelfRel());
        return vo;
    }

    @Override
    public UserVO createUser(UserVO request) {
        if(request == null) {
            throw new RequiredObjectIsNullException();
        }
        User user = DozerMapper.parseObject(request, User.class);
        user.setCreationDate(new Date());
        UserVO vo = DozerMapper.parseObject(repository.save(user), UserVO.class);

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
    public UserVO updateUser(UserVO request) {
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

        UserVO vo = DozerMapper.parseObject(repository.save(user), UserVO.class);

        //hateoas
        vo.add(linkTo(methodOn(UserController.class).getUser(vo.getKey())).withSelfRel());

        return vo;
    }
}

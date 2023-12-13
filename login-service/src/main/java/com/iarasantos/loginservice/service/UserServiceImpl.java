package com.iarasantos.loginservice.service;


import com.iarasantos.common.utilcommon.mapper.DozerMapper;
import com.iarasantos.loginservice.data.vo.v1.UserVO;
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
        List<User> users = repository.findAll();
        return DozerMapper.parseListObjects(users, UserVO.class);

    }

    @Override
    public UserVO getUser(Long userId) {
        User user = repository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + userId + " not found!"));
        return DozerMapper.parseObject(user, UserVO.class);
    }

    @Override
    public UserVO createUser(UserVO request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .role(request.getRole())
                .build();
        user.setCreationDate(new Date());
        return DozerMapper.parseObject(repository.save(user), UserVO.class);
    }

    @Override
    public void deleteUser(Long userId) {
        repository.deleteUserById(userId);
    }

    @Override
    public UserVO updateUser(UserVO request) {
        User user = repository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + request.getId() + " not found!"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        return DozerMapper.parseObject(repository.save(user), UserVO.class);
    }
}

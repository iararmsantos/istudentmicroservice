package com.iarasantos.loginservice.service;

import com.iarasantos.loginservice.data.vo.v1.UserVO;
import java.util.List;

public interface UserService {
    public List<UserVO> getUsers();

    UserVO getUser(Long userId);

    public UserVO createUser(UserVO request);

    public void deleteUser(Long id);

    public UserVO updateUser(UserVO request);
}

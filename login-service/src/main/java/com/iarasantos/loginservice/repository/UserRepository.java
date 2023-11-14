package com.iarasantos.loginservice.repository;

import com.iarasantos.loginservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long userId);

    void deleteUserById(Long userId);
}

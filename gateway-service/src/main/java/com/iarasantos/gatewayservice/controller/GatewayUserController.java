package com.iarasantos.gatewayservice.controller;

import com.iarasantos.gatewayservice.dto.UserRequest;
import com.iarasantos.gatewayservice.modal.User;
import com.iarasantos.gatewayservice.proxy.UserProxy;
import org.springframework.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class GatewayUserController {
    @Autowired
    private UserProxy userProxy;

    @GetMapping
    public User[] getUsers() { return userProxy.getUsers(); }

    @PostMapping
    public void create(@RequestBody User user) {
        userProxy.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") String id) {
        userProxy.remove(id);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable("id") String userId, @RequestBody UserRequest request) {
        userProxy.update(userId, new HttpEntity<UserRequest>(request).getBody());
    }
}

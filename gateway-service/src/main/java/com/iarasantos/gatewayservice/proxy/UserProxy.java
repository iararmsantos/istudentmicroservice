package com.iarasantos.gatewayservice.proxy;

import com.iarasantos.gatewayservice.dto.UserRequest;
import com.iarasantos.gatewayservice.modal.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserProxy {
    private final RestTemplate restTemplate;
    private final String url;

    public UserProxy(@Value("${user.url}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = new  RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    public User[] getUsers() {
        return restTemplate.getForObject(url, User[].class);
    }

    public void createUser(User user) {
        restTemplate.postForObject(url, user, User.class);
    }

    public void remove(String id) {
        restTemplate.delete(url + "/" + id);
    }

    public void update(String userId, UserRequest request) {
        restTemplate.patchForObject(url + "/" + userId, request, Void.class);
    }
}

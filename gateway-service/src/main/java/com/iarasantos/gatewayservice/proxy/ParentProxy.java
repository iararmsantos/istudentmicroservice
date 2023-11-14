package com.iarasantos.gatewayservice.proxy;

import com.iarasantos.gatewayservice.modal.Parent;
import com.iarasantos.gatewayservice.dto.ParentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ParentProxy {
    private final RestTemplate restTemplate;
    private final String url;

    public ParentProxy(@Value("${parent.url}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = new  RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    public Parent[] getParents() {
        return restTemplate.getForObject(url, Parent[].class);
    }

    public void createParent(Parent parent) {
        restTemplate.postForObject(url, parent, Parent.class);
    }

    public void remove(String id) {
        restTemplate.delete(url + "/" + id);
    }

    public void update(String parentId, HttpEntity<ParentRequest> request) {
        restTemplate.patchForObject(url + "/" + parentId, request, Void.class);
    }
}

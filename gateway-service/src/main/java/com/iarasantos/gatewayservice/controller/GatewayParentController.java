package com.iarasantos.gatewayservice.controller;

import com.iarasantos.gatewayservice.modal.Parent;
import com.iarasantos.gatewayservice.dto.ParentRequest;
import com.iarasantos.gatewayservice.proxy.ParentProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parents")
public class GatewayParentController {
    @Autowired
    private ParentProxy parentProxy;

    @GetMapping
    public Parent[] getParents() {
        return parentProxy.getParents();
    }

    @PostMapping
    public void create(@RequestBody Parent parent) {
        parentProxy.createParent(parent);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")String id) {
        parentProxy.remove(id);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable("id") String parentId, @RequestBody ParentRequest request) {
        parentProxy.update(parentId, new HttpEntity<ParentRequest>(request));
    }
}

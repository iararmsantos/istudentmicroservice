package com.iarasantos.parentservice.controller;

import com.iarasantos.common.utilcommon.util.MediaType;
import com.iarasantos.parentservice.data.vo.v1.ParentVO;
import com.iarasantos.parentservice.service.ParentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parents")
public class ParentController {
    @Autowired
    private ParentService service;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @ResponseStatus(HttpStatus.OK)
    public List<ParentVO> getParents() {
        return service.getParents();
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    @ResponseStatus(HttpStatus.CREATED)
    public ParentVO createParent(@Valid @RequestBody ParentVO request) {
        return service.createParent(request);
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    public ParentVO getParent(@PathVariable("id") Long parentId) {
        return service.getParentById(parentId);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParent(@PathVariable("id") Long parentId) {
        service.deleteParent(parentId);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                    MediaType.APPLICATION_YML})
    public ParentVO updateParent(@RequestBody ParentVO request) {
        return service.updateParent(request);
    }
}

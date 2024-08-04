package com.iarasantos.loginservice.config;

import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.model.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        TypeMap<UserEntity, UserRequest> propertyMapper = modelMapper.createTypeMap(UserEntity.class, UserRequest.class);
        propertyMapper.addMapping(UserEntity::getId, UserRequest::setKey);
        TypeMap<UserRequest, UserEntity> propertyMapper2 = modelMapper.createTypeMap(UserRequest.class, UserEntity.class);
        propertyMapper2.addMapping(UserRequest::getKey, UserEntity::setId);

        return modelMapper;
    }
}

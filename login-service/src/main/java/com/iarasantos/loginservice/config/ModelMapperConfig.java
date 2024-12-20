package com.iarasantos.loginservice.config;

import com.iarasantos.loginservice.data.vo.v1.UserRequest;
import com.iarasantos.loginservice.data.vo.v1.UserResponse;
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

        // Mapping for UserEntity to UserRequest
        TypeMap<UserEntity, UserRequest> propertyMapper1 = modelMapper.createTypeMap(UserEntity.class, UserRequest.class);
        propertyMapper1.addMapping(UserEntity::getId, UserRequest::setKey);

        // Mapping for UserRequest to UserEntity
        TypeMap<UserRequest, UserEntity> propertyMapper2 = modelMapper.createTypeMap(UserRequest.class, UserEntity.class);
        propertyMapper2.addMapping(UserRequest::getKey, UserEntity::setId);

        // Mapping for UserEntity to UserResponse
        TypeMap<UserEntity, UserResponse> propertyMapper3 = modelMapper.createTypeMap(UserEntity.class, UserResponse.class);
        propertyMapper3.addMappings(mapper -> {
            // Explicit field mappings
            mapper.map(UserEntity::getUserId, UserResponse::setUserId);
            mapper.map(UserEntity::getFirstName, UserResponse::setFirstName);
            mapper.map(UserEntity::getLastName, UserResponse::setLastName);
            mapper.map(UserEntity::getPhone, UserResponse::setPhone);
            mapper.map(UserEntity::getEmail, UserResponse::setEmail);
            mapper.map(UserEntity::getRole, UserResponse::setRole);
            mapper.map(UserEntity::getCreationDate, UserResponse::setCreationDate);

            // Skip ID if necessary
            mapper.skip(UserResponse::setId);
        });

        return modelMapper;
    }
}

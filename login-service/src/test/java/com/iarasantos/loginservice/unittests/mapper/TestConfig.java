package com.iarasantos.loginservice.unittests.mapper;

import com.iarasantos.loginservice.config.ModelMapperConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ModelMapperConfig.class)
public class TestConfig {
}

package com.iarasantos.loginservice.security;

import com.iarasantos.loginservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    private UserService usersService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment environment;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    public WebSecurity(UserService usersService, BCryptPasswordEncoder bCryptPasswordEncoder, Environment environment) {
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.environment = environment;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        String ipAddress = environment.getProperty("gateway.ip");
        logger.info("API Gateway IP Address: {}", ipAddress);
        WebExpressionAuthorizationManager  gatewayIp = new WebExpressionAuthorizationManager("hasIpAddress('" + environment.getProperty("gateway.ip") + "')");
        //Configure AuthenticationManagerBuilder
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);

//        authenticationManagerBuilder.userDetailsService(userService)
//                .passwordEncoder(bCryptPasswordEncoder);
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        //Create authenticationFilter
//        AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment, authenticationManager);
//        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
//        IpAddressMatcher hasIpAddress = new IpAddressMatcher(Objects.requireNonNull(environment.getProperty("gateway.ipv4")));
//        IpAddressMatcher hasIpv4Address = new IpAddressMatcher(Objects.requireNonNull(environment.getProperty("gateway.ip")));

        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers(new AntPathRequestMatcher("/api/users/**", "POST"))
//                        .permitAll())
                        .access(gatewayIp))
                .csrf(AbstractHttpConfigurer::disable)
//                .addFilter(authenticationFilter)
//                .authenticationManager(authenticationManager)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

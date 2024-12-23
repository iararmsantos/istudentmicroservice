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
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment environment;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final CorsConfigurationSource corsConfigurationSource;

    @Autowired
    public WebSecurity(UserService usersService, BCryptPasswordEncoder bCryptPasswordEncoder,
                       Environment environment, CorsConfigurationSource corsConfigurationSource) {
        this.userService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.environment = environment;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        String ipAddress = environment.getProperty("gateway.ip");
        logger.info("API Gateway IP Address: {}", ipAddress);
        WebExpressionAuthorizationManager  gatewayIp = new WebExpressionAuthorizationManager("hasIpAddress('" + environment.getProperty("gateway.ip") + "')");
        //Configure AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

//        Create authenticationFilter
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, userService, environment);
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));


        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/configuration/ui", "/swagger-resources/**","/configuration/security", "/swagger-resources")
                        .permitAll()
//                .requestMatchers("/api/**").authenticated()
                //uncomment to test this service
                        .requestMatchers("/api/**")
                        .permitAll()
                .anyRequest().denyAll())
//                        .access(gatewayIp))
//                .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfiguration -> corsConfiguration.configurationSource(corsConfigurationSource))
                .addFilter(authenticationFilter)
                .authenticationManager(authenticationManager)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

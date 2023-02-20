package com.jameseng.dsclients.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableResourceServer // implementar o ResourceServer do oauth 2
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtTokenStore jwtTokenStore;

    @Autowired
    private Environment environment;

    private static final String[] PUBLIC = {"/oauth/token", "/h2-console/**"};

    private static final String[] CLIENTS = {"/clients/**"};

    private static final String[] USERS = {"/users/**"};

    @Override // decodificar e analisar token
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(jwtTokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // liberar H2
        if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.authorizeRequests()
                .antMatchers(PUBLIC).permitAll()

                .antMatchers(HttpMethod.GET, CLIENTS).permitAll()
                .antMatchers(HttpMethod.POST, CLIENTS).permitAll()
                .antMatchers(HttpMethod.PUT, CLIENTS).hasAnyRole("CLIENT", "OPERATOR", "ADMIN")
                .antMatchers(HttpMethod.DELETE, CLIENTS).hasAnyRole("CLIENT", "OPERATOR", "ADMIN")

                .antMatchers(HttpMethod.GET, USERS).hasAnyRole("OPERATOR", "ADMIN")
                .antMatchers(HttpMethod.POST, USERS).hasAnyRole("OPERATOR", "ADMIN")

                .antMatchers(HttpMethod.PUT, USERS).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, USERS).hasRole("ADMIN");
    }
}
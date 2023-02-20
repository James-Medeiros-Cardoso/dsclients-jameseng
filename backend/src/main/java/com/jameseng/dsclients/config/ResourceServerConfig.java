package com.jameseng.dsclients.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer // implementar o ResourceServer do oauth 2
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private JwtTokenStore jwtTokenStore;

    private static final String[] PUBLIC = {"/oauth/token"};

    //private static final String[] OPERATOR_OR_ADMIN = { "/products/**", "/categories/**" };
    private static final String[] CLIENT_OR_OPERATOR_OR_ADMIN = {"/clients/**", "/users/**"};

    private static final String[] CLIENTS = {"/clients/**"};

    private static final String[] ADMIN = {"/users/**"};


    @Override // decodificar e analisar token
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(jwtTokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(PUBLIC).permitAll()
                //.antMatchers(CLIENTS).permitAll()
                .antMatchers(HttpMethod.GET, CLIENT_OR_OPERATOR_OR_ADMIN).permitAll()

                //.antMatchers(HttpMethod.POST, CLIENTS).hasRole("CLIENT");

                .anyRequest().hasAnyRole("CLIENT", "OPERATOR", "ADMIN");
    }
}
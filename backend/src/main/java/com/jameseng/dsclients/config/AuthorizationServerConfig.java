package com.jameseng.dsclients.config;

import com.jameseng.dsclients.components.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer // essa classe é que representa o AuthorizationServer do oauth
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private JwtTokenStore jwtTokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${security.oauth2.client.client-id}") // variável do application.properties
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientsSecret;

    @Value("${jwt.duration}")
    private Integer jwtDuration;

    @Autowired
    private JwtTokenEnhancer tokenEnhancer;

    /* -------------------------- */

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()"); // permitAll() = nome do método
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientId) // nome da aplicação / dsclients
                .secret(bCryptPasswordEncoder.encode(clientsSecret)) // senha da aplicação / dscatalog123
                .scopes("read", "write") // leitura e escrita
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(jwtDuration); // 24 horas
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter, tokenEnhancer));

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(chain); // informações adicionadas ao token
    }
}
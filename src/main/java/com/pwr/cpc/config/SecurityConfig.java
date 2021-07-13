package com.pwr.cpc.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.resource.authentication.*;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.*;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {



 // String OktaRedirect =new String("https://CPC-OKTA.azurewebsites.net/");
  String OktaRedirect =new String("http://localhost:8081/");

    // Create the Okta client registration
    ClientRegistration oktacientRegistration = ClientRegistration
            .withRegistrationId("okta")
            .tokenUri("https://xom-poc.okta.com/oauth2/auspdolvnEZiFfZhu5d6/v1/token")
            .clientId("0oarprsoofpoIXI4e5d6")
            .clientSecret("2bnQyMQ0tA7bOWXpPXJnAU_nhyXsuKrFKOIbD0Z8")
            .scope("openid")
            .authorizationUri("https://xom-poc.okta.com/oauth2/auspdolvnEZiFfZhu5d6/v1/authorize")
            .redirectUri(OktaRedirect)
            .authorizationGrantType(new AuthorizationGrantType("authorization_code"))
            .build();


    ClientRegistrationRepository oktaclientRegistrationRepository = new InMemoryClientRegistrationRepository(oktacientRegistration);



    OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(oktaclientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri(OktaRedirect);
        return successHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                // allow anonymous access to the root page
                .antMatchers("/","/oauth/revoke-token").permitAll()

                // all other requests
                .anyRequest().authenticated()

                // After we logout, redirect to root page, by default Spring will send you to /login?logout
                .and().logout().logoutSuccessUrl("/")

                // RP-initiated logout
                .and().logout().logoutSuccessHandler(oidcLogoutSuccessHandler())

                // enable OAuth2/OIDC
                .and().oauth2Login()
            .and()
                .oauth2ResourceServer().jwt();

    }


}

package com.bankdnc.springbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import static com.bankdnc.springbackend.constans.Constant.*;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomReactiveUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, AuthConverter jwtAuthConverter, AuthManager jwtAuthManager){

        AuthenticationWebFilter jwtAuthFilter = new AuthenticationWebFilter(jwtAuthManager);
        jwtAuthFilter.setServerAuthenticationConverter(jwtAuthConverter);

        http
                .csrf().disable()
                .authorizeExchange(auth -> {
                    //auth
                    auth.pathMatchers(ENDPOINT_AUTH+REGISTER).permitAll();
                    auth.pathMatchers(ENDPOINT_AUTH+LOGIN).permitAll();

                    //swagger
                    auth.pathMatchers("/v3/api-docs/**").permitAll();
                    auth.pathMatchers("/swagger-resources/**").permitAll();
                    auth.pathMatchers("/swagger-ui.html").permitAll();
                    auth.pathMatchers("/webjars/**").permitAll();
                    auth.pathMatchers("/swagger-ui/**").permitAll();

                    auth.anyExchange().authenticated();
                })
                .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic().disable()
                .formLogin().disable();

        return http.build();
    }

}
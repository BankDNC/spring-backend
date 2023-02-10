package com.bankdnc.springbackend.security;

import com.bankdnc.springbackend.model.documents.User;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthManager implements ReactiveAuthenticationManager {
    private final JwtService jwtService;
    private final ReactiveUserDetailsService userDetailsService;

    public AuthManager(JwtService jwtService, ReactiveUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .cast(BearerToken.class)
                .flatMap(auth ->{
                    String username = null;
                    try{
                        username = jwtService.extractUsername(auth.getCredentials());
                    }catch (Exception e){
                        return Mono.error(new IllegalArgumentException("Token not valid"));
                    }

                    Mono<UserDetails> foundUser = userDetailsService.findByUsername(username)
                            .defaultIfEmpty(new User());

                    return foundUser.flatMap(u ->{
                        if (u.getUsername() == null){
                            Mono.error(new IllegalArgumentException("User not found"));
                        }
                        if(jwtService.isTokenValid(auth.getCredentials(), u)){
                            return Mono.just(new UsernamePasswordAuthenticationToken(u, auth.getCredentials(), u.getAuthorities()));
                        }
                        Mono.error(new IllegalArgumentException("Token not valid"));
                        return Mono.just(new UsernamePasswordAuthenticationToken(u, auth.getCredentials(), u.getAuthorities()));
                    });
                });
    }
}

package com.bankdnc.springbackend.controllers;

import com.bankdnc.springbackend.model.requests.LoginRequest;
import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.TokenResponse;
import com.bankdnc.springbackend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.bankdnc.springbackend.constans.Constant.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(ENDPOINT_AUTH)
@Tag(name = "auth", description = "The auth API")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(REGISTER)
    @Operation(summary = "Register a new user", description = "Register a new user", tags = { "auth" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST - Invalid input", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
            }),
            @ApiResponse(responseCode = "409", description = "CONFLICT - Email or DNI already exists", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
            })
    })

    public Mono<ResponseEntity<TokenResponse>> register(@Valid
                                                            @RequestBody
                                                            @Parameter(name = "userRequest", description = "User to register")
                                                            UserRequest userRequest){
        return authService.register(userRequest);
    }

    @PostMapping(LOGIN)
    @Operation(summary = "Login a user", description = "Login a user", tags = { "auth" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED - Invalid credentials", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))
            })
    })
    public Mono<ResponseEntity<TokenResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @GetMapping("/me")
    @Operation(summary = "Get the logged user", description = "Get the logged user", tags = { "auth" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED - Invalid credentials", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN - User not logged", content = {
                    @Content(mediaType = "application/json")
            })
    })
    public Mono<ResponseEntity<String>> me(){
        return Mono.just(new ResponseEntity<>("me", OK));
    }
}

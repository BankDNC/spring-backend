package com.bankdnc.springbackend;

import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.repository.UserRepository;
import com.bankdnc.springbackend.model.requests.LoginRequest;
import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.TokenResponse;
import com.bankdnc.springbackend.security.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.bankdnc.springbackend.constans.Constant.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
class AuthControllerTests {

	@MockBean
	UserRepository userRepository;
	@MockBean
	PasswordEncoder passwordEncoder;
	@MockBean
	JwtService jwtService;

	@Autowired
	private WebTestClient webClient;

	@Test
	void testCreateUser() {
		UserRequest userRequest = createUserRequest();

		User user = createUser();

		when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(Mono.just(false));
		when(userRepository.existsByDni(Mockito.anyString())).thenReturn(Mono.just(false));
		when(userRepository.save(Mockito.any(User.class))).thenReturn(Mono.just(user));

		webClient.post()
				.uri(ENDPOINT_AUTH + REGISTER)
				.bodyValue(userRequest)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody(TokenResponse.class);

	}
	@Test
	void testFailCreateUserByExistsByEmail() {
		UserRequest userRequest = createUserRequest();

		User user = createUser();

		when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(Mono.just(true));
		when(userRepository.existsByDni(Mockito.anyString())).thenReturn(Mono.just(false));
		when(userRepository.save(Mockito.any(User.class))).thenReturn(Mono.just(user));

		webClient.post()
				.uri(ENDPOINT_AUTH + REGISTER)
				.bodyValue(userRequest)
				.exchange()
				.expectStatus().is4xxClientError()
				.expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
				.expectBody(ProblemDetail.class);

	}

	@Test
	void testFailCreateUserByExistsByDni() {
		UserRequest userRequest = createUserRequest();

		User user = createUser();

		when(userRepository.existsByEmail(Mockito.anyString())).thenReturn(Mono.just(false));
		when(userRepository.existsByDni(Mockito.anyString())).thenReturn(Mono.just(true));
		when(userRepository.save(Mockito.any(User.class))).thenReturn(Mono.just(user));

		webClient.post()
				.uri(ENDPOINT_AUTH + REGISTER)
				.bodyValue(userRequest)
				.exchange()
				.expectStatus().is4xxClientError()
				.expectHeader().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
				.expectBody(ProblemDetail.class);

	}

	@Test
	void testLoginUser() {
		LoginRequest loginRequest = createUserLoginRequest();

		User user = createUser();

		when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Mono.just(user));
		when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("token");

		webClient.post()
				.uri(ENDPOINT_AUTH+LOGIN)
				.bodyValue(loginRequest)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody(TokenResponse.class);
	}

	@Test
	void testFailLoginUserByNotExistsEmail() {
		LoginRequest loginRequest = createUserLoginRequest();

		User user = createUser();

		when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Mono.empty());

		webClient.post()
				.uri(ENDPOINT_AUTH+LOGIN)
				.bodyValue(loginRequest)
				.exchange()
				.expectStatus().is4xxClientError()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody(ProblemDetail.class);
	}

	@Test
	void testFailLoginUserByNotMatchPassword() {
		LoginRequest loginRequest = createUserLoginRequest();

		User user = createUser();

		when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Mono.just(user));
		when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

		webClient.post()
				.uri(ENDPOINT_AUTH+LOGIN)
				.bodyValue(loginRequest)
				.exchange()
				.expectStatus().is4xxClientError()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody(ProblemDetail.class);
	}

	private static UserRequest createUserRequest() {
		return UserRequest.builder()
				.name("name")
				.lastName("lastName")
				.email("email@gmail.com")
				.password("password")
				.dni("dni")
				.phone("phone")
				.build();
	}
	private static LoginRequest createUserLoginRequest() {
		return LoginRequest.builder()
				.email("test@test.com")
				.password("password")
				.build();
	}
	private static User createUser() {
		return User.builder()
				.name("name")
				.lastName("lastName")
				.email("test@test.com")
				.password("password")
				.dni("dni")
				.phone("phone")
				.build();
	}
}

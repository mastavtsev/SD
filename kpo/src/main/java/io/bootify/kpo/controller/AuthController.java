package io.bootify.kpo.controller;

import io.bootify.kpo.domain.JwtUtil;
import io.bootify.kpo.dto.UserAuthRequest;
import io.bootify.kpo.exception.UnauthorizedException;
import io.bootify.kpo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;

    private final AuthService authService;

    /**
     * Endpoint for user authentication.
     * It receives a POST request with the user's authentication credentials.
     * The credentials are passed as a UserAuthRequest object in the request body.
     * The AuthService is used to authenticate the user by calling the authService.login() method.
     * If the authentication is successful, a JWT token is generated using the JwtUtil.generateToken() method.
     * The token is returned in the response as an AuthResponse object.
     * If the authentication fails, an UnauthorizedException is thrown with the message "Invalid email or password."
     *
     * @param request the UserAuthRequest object containing the user's authentication credentials
     * @return an AuthResponse object containing the generated JWT token
     * @throws UnauthorizedException if the authentication fails
     */
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthResponse login(@RequestBody UserAuthRequest request) {
        boolean authenticated = authService.login(request);

        if (authenticated) {
            String token = generateToken(request.getEmail());

            return new AuthResponse(token);
        } else {
            throw new UnauthorizedException("Invalid email or password.");
        }
    }

    /**
     * Generates a JWT token for the given email.
     *
     * @param email the email used to generate the token
     * @return the generated JWT token
     */
    private String generateToken(String email) {
        return jwtUtil.generateToken(email);
    }

    /**
     * Represents the response object returned by the login endpoint.
     */
    private static class AuthResponse {
        private final String token;

        public AuthResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}


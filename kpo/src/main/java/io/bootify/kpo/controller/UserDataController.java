package io.bootify.kpo.controller;

import io.bootify.kpo.domain.User;
import io.bootify.kpo.exception.UnauthorizedException;
import io.bootify.kpo.services.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserDataController {

    private final UserDataService userDataService;

    /**
     * Endpoint to retrieve user information.
     * It receives a GET request with the Authorization header containing the user's token.
     * The token is used to retrieve the user information from the database through the userDataService.getUserFromDatabase() method.
     * If the user exists, it is returned as the response.
     * If the user does not exist, an UnauthorizedException is thrown with the message "Invalid token."
     *
     * @param token the user's token extracted from the Authorization header
     * @return the User object containing the user information
     * @throws UnauthorizedException if the token is invalid or the user does not exist
     */
    @GetMapping
    public Optional<User> getUserInfo(@RequestHeader("Authorization") String token) {
        // Retrieve user information from the database
        Optional<User> user = userDataService.getUserFromDatabase(token);

        if (user.isPresent()) {
            return user;
        } else {
            throw new UnauthorizedException("Invalid token.");
        }
    }
}

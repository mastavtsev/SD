package io.bootify.kpo.controller;

import io.bootify.kpo.dto.UserRegistrationRequest;
import io.bootify.kpo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor

public class RegistrationController {

    private final UserService userService;

    /**
     * Endpoint to register a new user.
     * It receives a POST request with the user registration data in the request body.
     * The request body is mapped to the UserRegistrationRequest object.
     * The userService.registerUser() method is invoked to register the user.
     * If the registration is successful, the response status is set to HttpStatus.CREATED.
     *
     * @param request the UserRegistrationRequest object containing the user registration data
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserRegistrationRequest request) {
        userService.registerUser(request);
    }
}

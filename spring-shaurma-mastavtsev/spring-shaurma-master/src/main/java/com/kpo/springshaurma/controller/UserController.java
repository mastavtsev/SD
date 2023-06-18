package com.kpo.springshaurma.controller;

import com.kpo.springshaurma.api.model.LoginRequest;
import com.kpo.springshaurma.api.model.RegistrationRequest;
import com.kpo.springshaurma.api.model.Response;
import com.kpo.springshaurma.api.model.User;
import com.kpo.springshaurma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Response> registration(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(userService.createUser(registrationRequest));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<String> activate(@PathVariable String code) {
        if (userService.activateUser(code).getSuccess()) {
            return ResponseEntity.ok("Удачно");
        } else {
            return ResponseEntity.ok("Ссылка устарела");
        }
    }

    @GetMapping("/reset-password")
    public ResponseEntity<Response> resetPassword(@RequestPart String email) {
        return ResponseEntity.ok(userService.resetPassword(email));
    }

    @GetMapping("/check-token")
    public ResponseEntity<User> checkToken(@RequestPart String token) {
        return ResponseEntity.ok(userService.checkToken(token));
    }
}

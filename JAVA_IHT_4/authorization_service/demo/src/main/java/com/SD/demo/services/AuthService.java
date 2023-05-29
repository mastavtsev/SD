package com.SD.demo.services;

import com.SD.demo.dto.UserAuthRequest;
import com.SD.demo.exception.InvalidEmailException;
import com.SD.demo.model.User;
import com.SD.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Service class for user authentication.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;

    /**
     * Performs user login by verifying the provided credentials.
     *
     * @param userAuthRequest the user authentication request containing the email and password
     * @return true if the login is successful, false otherwise
     */
    public boolean login(UserAuthRequest userAuthRequest) {

        User user = userRepository.findByUserEmail(userAuthRequest.getEmail());

        // Check if the user exists
        if (user == null) {
            return false;
        }


        // Verify the password
        return BCrypt.checkpw(userAuthRequest.getPassword(), user.getPassword());
    }
}

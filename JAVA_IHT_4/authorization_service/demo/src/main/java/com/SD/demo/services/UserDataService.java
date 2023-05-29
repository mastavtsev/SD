package com.SD.demo.services;


import com.SD.demo.model.JwtUtil;
import com.SD.demo.model.User;
import com.SD.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class for retrieving user data from the database based on a token.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDataService {
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    /**
     * Retrieves a user from the database based on the provided token.
     * @param token the token used to retrieve the user
     * @return the user associated with the token, or null if the token is invalid or the user is not found
     */
    public User getUserFromDatabase(String token) {

        if (validateToken(token)) {
            String email = jwtUtil.getEmailFromToken(token);
            return userRepository.findByUserEmail(email);
        }

        return null;
    }

    /**
     * Validates a token using the JwtUtil.
     * @param token the token to validate
     * @return true if the token is valid, false otherwise
     */
    private boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}

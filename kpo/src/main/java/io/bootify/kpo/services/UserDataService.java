package io.bootify.kpo.services;

import io.bootify.kpo.domain.JwtUtil;
import io.bootify.kpo.domain.User;
import io.bootify.kpo.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
     *
     * @param token the token used to retrieve the user
     * @return the user associated with the token, or null if the token is invalid or the user is not found
     */
    public Optional<User> getUserFromDatabase(String token) {

        //if (validateToken(token)) {
            String email = jwtUtil.getEmailFromToken(token);
            log.info("Email is {}", email);
            return userRepository.findByEmail(email);
        //}

        //return null;
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

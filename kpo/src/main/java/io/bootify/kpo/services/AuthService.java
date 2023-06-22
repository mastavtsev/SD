package io.bootify.kpo.services;


import io.bootify.kpo.domain.User;
import io.bootify.kpo.dto.UserAuthRequest;
import io.bootify.kpo.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        Optional<User> user = userRepository.findByEmail(userAuthRequest.getEmail());

        // Check if the user exists
        if (!user.isPresent()) {
            return false;
        }


        // Verify the password
        //return BCrypt.checkpw(userAuthRequest.getPassword(), user.get().getPassword());
        return userAuthRequest.getPassword().equals(user.get().getPassword());
        //return true;
    }
}

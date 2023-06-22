package io.bootify.kpo.services;


import io.bootify.kpo.domain.User;
import io.bootify.kpo.dto.UserRegistrationRequest;
import io.bootify.kpo.exception.InvalidEmailException;
import io.bootify.kpo.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Service class for user-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    /**
     * Registers a new user based on the provided registration request.
     *
     * @param userRegistrationRequest the registration request containing user details
     */
    public void registerUser(UserRegistrationRequest userRegistrationRequest) {

        User user = User.builder()
                .username(userRegistrationRequest.getUsername())
                .email(userRegistrationRequest.getEmail())
                .role(userRegistrationRequest.getRole())
                .build();


        if (!isValidEmail(user.getEmail())) {
            throw new InvalidEmailException("Invalid email address provided.");
        }

        // Check if the username is already taken
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Hash the user's password
        //String hashedPassword = BCrypt.hashpw(userRegistrationRequest.getPassword(), BCrypt.gensalt());
        String hashedPassword = userRegistrationRequest.getPassword();
        // Set the hashed password
        user.setPassword(hashedPassword);

        // Save the user in the database
        userRepository.save(user);

        log.info("User {} has been created!", user.getUsername());
    }

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Validates the format of an email address.
     *
     * @param email the email address to validate
     * @return true if the email address is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

}

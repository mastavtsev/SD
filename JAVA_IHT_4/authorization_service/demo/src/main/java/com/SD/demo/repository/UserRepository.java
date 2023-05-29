package com.SD.demo.repository;

import com.SD.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for managing user data in MongoDB.
 */
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Retrieves a user by their email address.
     *
     * @param userEmail the email address of the user
     * @return the user with the specified email address, or null if not found
     */
    User findByUserEmail(String userEmail);
}

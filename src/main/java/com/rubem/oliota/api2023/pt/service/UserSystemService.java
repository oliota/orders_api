package com.rubem.oliota.api2023.pt.service;

import com.rubem.oliota.api2023.pt.model.UserSystem;
import com.rubem.oliota.api2023.pt.repository.UserSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing user system entities.
 */
@Service
public class UserSystemService {

    @Autowired
    private UserSystemRepository userRepository;

    /**
     * Retrieves a list of all user system entities.
     *
     * @return A list of all user system entities.
     */
    public List<UserSystem> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user system entity by its ID.
     *
     * @param id The ID of the user system entity to retrieve.
     * @return The user system entity with the specified ID, or null if not found.
     */
    public UserSystem getUserById(Long id) {
        Optional<UserSystem> user = userRepository.findById(id);
        return user.orElse(null);
    }

    /**
     * Creates a new user system entity.
     *
     * @param user The user system entity to create.
     * @return The created user system entity.
     */
    public UserSystem createUser(UserSystem user) {
        return userRepository.save(user);
    }

    /**
     * Updates an existing user system entity.
     *
     * @param id          The ID of the user system entity to update.
     * @param updatedUser The updated user system entity.
     * @return The updated user system entity, or null if the user was not found.
     */
    public UserSystem updateUser(Long id, UserSystem updatedUser) {
        Optional<UserSystem> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserSystem existingUser = userOptional.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());

            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }

    /**
     * Deletes a user system entity by its ID.
     *
     * @param id The ID of the user system entity to delete.
     * @return True if the user system entity was deleted, false if it was not found.
     */
    public boolean deleteUser(Long id) {
        Optional<UserSystem> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
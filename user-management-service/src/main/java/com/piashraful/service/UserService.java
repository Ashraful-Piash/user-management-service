package com.piashraful.service;

import com.piashraful.entity.UserEntity;
import com.piashraful.model.User;
import com.piashraful.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        log.info("Saving user: {}", user);

        validateUser(user);

        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        UserEntity savedUserEntity = userRepository.save(userEntity);

        // Set the generated ID back into the User model
        user.setId(savedUserEntity.getId());

        log.info("User saved successfully: {}", user);
        return user;
    }

    public Optional<User> getUserById(Long id) {
        log.info("Retrieving user by ID: {}", id);

        Optional<User> user = userRepository.findById(id).map(this::convertToUser);

        log.info("User retrieved by ID {}: {}", id, user.orElse(null));
        return user;
    }

    public User updateUser(Long id, User user) {
        log.info("Updating user with ID {}: {}", id, user);

        validateUser(user);

        Optional<UserEntity> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            UserEntity existingUser = existingUserOptional.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            UserEntity updatedUserEntity = userRepository.save(existingUser);

            User updatedUser = convertToUser(updatedUserEntity);

            log.info("User updated successfully: {}", updatedUser);
            return updatedUser;
        } else {
            log.error("User not found with ID: {}", id);
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "User not found with id: " + id);
        }
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);

        userRepository.deleteById(id);

        log.info("User deleted successfully with ID: {}", id);
    }

    private void validateUser(User user) {
        if (user == null || user.getName() == null || user.getEmail() == null) {
            log.error("Invalid user data: {}", user);
            throw new IllegalArgumentException("User, name, or email cannot be null");
        }
    }

    private User convertToUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }
}


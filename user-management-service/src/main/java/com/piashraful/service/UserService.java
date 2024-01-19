package com.piashraful.service;

import com.piashraful.entity.UserEntity;
import com.piashraful.exception.InvalidUserDataException;
import com.piashraful.exception.UserNotFoundException;
import com.piashraful.model.User;
import com.piashraful.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        log.info("Saving user: {}", user);
        validateUser(user);

        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());

        UserEntity savedUserEntity = userRepository.save(userEntity);

        User savedUser = convertToUser(savedUserEntity);

        log.info("User saved successfully: {}", savedUser);
        return savedUser;
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
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);

        userRepository.deleteById(id);

        log.info("User deleted successfully with ID: {}", id);
    }

    private void validateUser(User user) {
        if (user == null || user.getName() == null || user.getEmail() == null) {
            log.warn("Invalid user data: {}", user);
            throw new InvalidUserDataException("User, name, or email cannot be null");
        }
    }

    private User convertToUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getEmail());
    }
}


package com.piashraful.service;

import com.piashraful.entity.UserEntity;
import com.piashraful.model.User;
import com.piashraful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity saveUser(User user) {
        validateUser(user);

        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserEntity updateUser(Long id, User user) {
        validateUser(user);

        Optional<UserEntity> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            UserEntity existingUser = existingUserOptional.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        } else {
            throw new NoSuchElementException("User not found with id: " + id);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private void validateUser(User user) {
        if (user == null || user.getName() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("User, name, or email cannot be null");
        }
    }
}



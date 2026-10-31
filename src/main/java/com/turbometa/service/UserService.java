package com.turbometa.service;

import com.turbometa.model.User;
import com.turbometa.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> search(String query) {
        if (query == null || query.isBlank()) {
            return findAll();
        }
        return userRepository.search(query.trim());
    }

    @Transactional
    public User create(String username, String email, String rawPassword, boolean isAdmin) {
        validateUsername(username);
        validateEmail(email);

        if (userRepository.emailExists(email)) {
            throw new IllegalArgumentException("Email already in use: " + email);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setActive(true);
        user.setAdmin(isAdmin);

        userRepository.insert(user);
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void update(User user, String rawPassword) {
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());

        if (userRepository.emailExists(user.getEmail(), user.getId())) {
            throw new IllegalArgumentException("Email already in use: " + user.getEmail());
        }

        // Prevent deactivating the last active admin
        if (user.isAdmin() && !user.isActive() && userRepository.countActiveAdmins() <= 1) {
            throw new IllegalArgumentException("Cannot deactivate the last active administrator");
        }

        if (rawPassword != null && !rawPassword.isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(rawPassword));
            userRepository.update(user);
        } else {
            userRepository.updateWithoutPassword(user);
        }
    }

    @Transactional
    public void delete(Long id) {
        User user = findById(id);
        if (user.isAdmin() && user.isActive() && userRepository.countActiveAdmins() <= 1) {
            throw new IllegalArgumentException("Cannot delete the last active administrator");
        }
        userRepository.delete(id);
    }

    @Transactional
    public void toggleActive(Long id) {
        User user = findById(id);
        boolean newActiveState = !user.isActive();

        // Prevent deactivating the last active admin
        if (user.isAdmin() && !newActiveState && userRepository.countActiveAdmins() <= 1) {
            throw new IllegalArgumentException("Cannot deactivate the last active administrator");
        }

        user.setActive(newActiveState);
        userRepository.updateWithoutPassword(user);
    }

    private void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (username.length() > 30) {
            throw new IllegalArgumentException("Username must be 30 characters or less");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}

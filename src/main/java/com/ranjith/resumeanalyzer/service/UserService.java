package com.ranjith.resumeanalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ranjith.resumeanalyzer.entity.User;
import com.ranjith.resumeanalyzer.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register User
    public User register(User user) {

        // normalize email to lower-case to avoid case-sensitivity issues
        String email = user.getEmail() == null ? null : user.getEmail().trim().toLowerCase();
        user.setEmail(email);

        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User getByEmail(String email) {
        String e = email == null ? null : email.trim().toLowerCase();
        return userRepository.findByEmail(e)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateName(String email, String name) {
        User user = getByEmail(email);
        user.setName(name);
        return userRepository.save(user);
    }

    public User changePassword(String email,
                               String currentPassword,
                               String newPassword) {
        User user = getByEmail(email);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    public User resetPassword(String email, String newPassword) {
        User user = getByEmail(email);

        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    // Login User
    public User loginUser(String email, String password) {

        String e = email == null ? null : email.trim().toLowerCase();

        User user = userRepository.findByEmail(e)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        return user;
    }

    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public long getTotalUsers() {
        return userRepository.count();
    }
}
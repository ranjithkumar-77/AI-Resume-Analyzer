package com.ranjith.resumeanalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ranjith.resumeanalyzer.dto.PasswordChangeRequest;
import com.ranjith.resumeanalyzer.dto.PasswordResetRequest;
import com.ranjith.resumeanalyzer.entity.User;
import com.ranjith.resumeanalyzer.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        System.out.println("Register API Called");

        return userService.register(user);
    }

    // Login
    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {

        System.out.println("Login API Called");

        return userService.loginUser(
                user.getEmail(),
                user.getPassword());
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getByEmail(email);
    }

    @PutMapping("/{email}")
    public User updateProfile(
            @PathVariable String email,
            @RequestBody User user) {

        return userService.updateName(email, user.getName());
    }

    @PutMapping("/change-password")
    public String changePassword(
            @RequestBody PasswordChangeRequest request) {

        userService.changePassword(
                request.getEmail(),
                request.getCurrentPassword(),
                request.getNewPassword());

        return "Password changed successfully.";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestBody PasswordResetRequest request) {

        userService.resetPassword(
                request.getEmail(),
                request.getNewPassword());

        return "Password reset successfully.";
    }
}
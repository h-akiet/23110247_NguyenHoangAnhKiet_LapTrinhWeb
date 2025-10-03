package vn.iotstar.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // <-- CORRECTED IMPORT
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List; // <-- MISSING IMPORT
import vn.iotstar.entity.User;
import vn.iotstar.services.UserService;

@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        // Retrieve the current authentication object from the Spring Security Context
        // Fixed: Use the correct Spring Security Authentication interface
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // The principal is the object that represents the user,
        // which, in this setup, is the User entity.
        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }
}
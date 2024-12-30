package com.mihir.musiclibrary.user.controllers;

import com.mihir.musiclibrary.Response.ApiResponse;
import com.mihir.musiclibrary.Response.ErrorDetails;
import com.mihir.musiclibrary.user.dto.UserDto;
import com.mihir.musiclibrary.user.entity.UserEntity;
import com.mihir.musiclibrary.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signup(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            List<ErrorDetails> errorDetails = bindingResult.getFieldErrors().stream()
                    .map(error -> new ErrorDetails(error.getField(), error.getDefaultMessage()))
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null, "Validation failed", errorDetails));
        }

        if (userService.emailExists(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(409, null, "Email already exists.", null));
        }

        UserEntity savedUser = userService.saveUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, null, "User created successfully.", null));
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody UserDto userDto) {
        String token = userService.authenticateUser(userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok(new ApiResponse<>(200, Map.of("token", token), "Login successful.", null));
    }

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getAllUsers(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String role,
            @RequestHeader("Authorization") String token) {

        if (!userService.isAdminAccessValid(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse<>(403, null, "Forbidden: Access denied.", null));
        }

        List<UserEntity> users = userService.getUsers(limit, offset, role);
        if(users.isEmpty())
            return ResponseEntity.ok(new ApiResponse<>(200, null, "No users found", null));
        return ResponseEntity.ok(new ApiResponse<>(200, users, "Users retrieved successfully.", null));
    }

    // Add a new user
    @PostMapping("/users/add-user")
    public ResponseEntity<ApiResponse<?>> addUser(
            @Valid @RequestBody UserDto userDto,
            @RequestHeader("Authorization") String token,
            BindingResult bindingResult) {

        if (!userService.isAdminAccessValid(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse<>(403, null, "Forbidden: Access denied.", null));
        }

        if (bindingResult.hasErrors()) {
            List<ErrorDetails> errorDetails = bindingResult.getFieldErrors().stream()
                    .map(error -> new ErrorDetails(error.getField(), error.getDefaultMessage()))
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null, "Validation failed", errorDetails));
        }

        if (userService.emailExists(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(409, null, "Email already exists.", null));
        }

        UserEntity createdUser = userService.saveUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, createdUser, "User created successfully.", null));
    }

    // Delete a user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String token) {

        if (!userService.isAdminAccessValid(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse<>(403, null, "Forbidden: Access denied.", null));
        }

        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(200, null, "User deleted successfully.", null));
    }

    // Update password
    @PutMapping("/users/update-password")
    public ResponseEntity<ApiResponse<?>> updatePassword(
            @RequestBody Map<String, String> passwords,
            @RequestHeader("Authorization") String token) {

        if (passwords.get("old_password") == null || passwords.get("new_password") == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null, "Bad Request: Missing required fields.", null));
        }

        if (Objects.equals(passwords.get("old_password"), passwords.get("new_password"))) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null, "Bad Request: New password cannot be the same as the old password.", null));
        }

        userService.updatePassword(token, passwords.get("old_password"), passwords.get("new_password"));
        return ResponseEntity.ok(new ApiResponse<>(204, null, "Password updated successfully.", null));
    }
}

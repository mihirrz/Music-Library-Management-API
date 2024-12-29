package com.mihir.musiclibrary.user.dto;

import com.mihir.musiclibrary.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {

    private UUID userId;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotNull(message = "Role is required.")
    private UserRole role;

    public UserDto() {}

    public UserDto(UUID userId, String email, String password, UserRole role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UUID getUserId() { return userId; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) { this.role = role; }
}

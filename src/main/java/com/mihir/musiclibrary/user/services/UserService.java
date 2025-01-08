package com.mihir.musiclibrary.user.services;

import com.mihir.musiclibrary.user.Repository.UserRepository;
import com.mihir.musiclibrary.auth.JwtService;
import com.mihir.musiclibrary.user.dto.UserDto;
import com.mihir.musiclibrary.user.entity.UserEntity;
import com.mihir.musiclibrary.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public UserEntity saveUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if (isFirstUser()) {
            user.setRole(UserRole.ROLE_ADMIN);
        } else {
            if (userDto.getRole() == null)
                handleRoleException("Role is missing");
            if(userDto.getRole() == UserRole.ROLE_ADMIN)
                handleRoleException("An Admin role has already been assigned to the organization. " +
                                    "As part of our 'One Organization, One Admin' policy, no new Admins can be created. " +
                                    "Please contact the existing Admin for further assistance."
                );
            user.setRole(userDto.getRole());
        }

        return userRepository.save(user);
    }

    public String authenticateUser(String email, String password) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(email, String.valueOf(user.getRole()));
    }

    public List<UserEntity> getUsers(int limit, int offset, String role) {
        return userRepository.findAll();
    }

    public void deleteUser(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public void updatePassword(String token, String oldPassword, String newPassword) {
        String email = jwtService.extractEmail(token.substring(7));
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Incorrect old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public boolean isFirstUser() {
        return userRepository.count() == 0;
    }

    private void handleRoleException(String message) {
        throw new RuntimeException(message);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}

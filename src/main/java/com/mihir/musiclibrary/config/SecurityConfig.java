package com.mihir.musiclibrary.config;

import com.mihir.musiclibrary.auth.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for simplicity (consider enabling in production)
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) // Stateless sessions
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry

                                // Public access for signup and login
                                .requestMatchers(HttpMethod.POST,"/api/v1/signup", "/api/v1/login").permitAll()

                                // Role-based access for users
                                .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/v1/users/add-user").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/update-password").authenticated()

                                // Role-based access for artists
                                .requestMatchers(HttpMethod.GET, "/api/v1/artists").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR", "ROLE_VIEWER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/artists/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR", "ROLE_VIEWER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/artists/add-artist").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/artists/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/artists/**").hasAuthority("ROLE_ADMIN")

                                // Role-based access for albums
                                .requestMatchers(HttpMethod.GET, "/api/v1/albums").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR", "ROLE_VIEWER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/albums/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR", "ROLE_VIEWER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/albums/add-album").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/albums/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/albums/**").hasAuthority("ROLE_ADMIN")

                                // Role-based access for tracks
                                .requestMatchers(HttpMethod.GET, "/api/v1/tracks").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR", "ROLE_VIEWER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/tracks/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR", "ROLE_VIEWER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/tracks/add-track").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/tracks/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/tracks/**").hasAuthority("ROLE_ADMIN")

                                // Role-based access for favorites
                                .requestMatchers(HttpMethod.GET, "/api/v1/favorites/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR", "ROLE_VIEWER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/favorites/add-favorite").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/favorites/remove-favorite/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")

                                // Require authentication for all other endpoints
                                .anyRequest().authenticated()
                )
                // Register custom access denied handler
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.accessDeniedHandler(customAccessDeniedHandler)
                )
                // Custom JWT authentication filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.getOrBuild();
    }

}
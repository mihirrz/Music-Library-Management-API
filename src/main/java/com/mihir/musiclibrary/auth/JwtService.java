package com.mihir.musiclibrary.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Store SECRET_KEY securely in environment variables or application.properties for production
    private static final String SECRET_KEY = "Thisismihirssdyhdcfvdftygjxsrtcfgvcfyucfgztxgvnjhftyecretkey";

    // Generate a JWT token with user email and role
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email) // Store user email as the subject
                .claim("role", role) // Store role in the claims
                .setIssuedAt(new Date()) // Issue date
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token expires in 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Sign with the secret key
                .compact(); // Return the compact JWT token
    }

    // Validate token: Check if token is not expired and email matches
    public boolean validateToken(String token, String email) {
        final String tokenEmail = extractEmail(token); // Extract the email from the token
        return (tokenEmail.equals(email) && !isTokenExpired(token)); // Validate token
    }

    // Extract the email from the token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject); // Extract the subject (email)
    }

    // Check if the token has expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compare expiration date with current date
    }

    // Extract expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Extract expiration date
    }

    // Extract a specific claim (like role or email) from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Extract all claims from the token
        return claimsResolver.apply(claims); // Apply the claims resolver function to the claims
    }

    // Extract all claims from the token
    public static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // Set the signing key
                .build()
                .parseClaimsJws(token) // Parse the JWT and get the claims
                .getBody();
    }
}

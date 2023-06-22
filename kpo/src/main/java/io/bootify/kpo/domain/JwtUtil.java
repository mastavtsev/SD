package io.bootify.kpo.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;


    /**
     * Generates a JWT token for the specified email.
     *
     * @param email the email address
     * @return the generated JWT token
     */
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 24);

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        var key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS384)
                .compact();
    }

    /**
     * Retrieves the email address stored in the JWT token.
     *
     * @param token the JWT token
     * @return the email address
     */
    public String getEmailFromToken(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        var key = Keys.hmacShaKeyFor(keyBytes);

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Validates the JWT token.
     *
     * @param token the JWT token
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secret);
            var key = Keys.hmacShaKeyFor(keyBytes);

            Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


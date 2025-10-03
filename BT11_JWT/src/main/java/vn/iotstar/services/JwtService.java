package vn.iotstar.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    // Injected from application properties
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    // --- PUBLIC METHODS ---

    /**
     * Extracts the username (subject) from a JWT token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generates a token with no extra claims, using the configured expiration time.
     */
    public String generateToken(UserDetails userDetails) {
        // Call the core building method using the configured expiration time.
        return buildToken(new HashMap<>(), userDetails, jwtExpiration);
    }

    /**
     * Checks if a token is valid for a given user.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Returns the configured expiration time.
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    // --- PRIVATE UTILITY METHODS ---

    /**
     * Extracts a specific claim from the token using a claimsResolver function.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Core method for building a token.
     * Renamed from 'buildToken1' to 'buildToken' for clarity.
     */
    private String buildToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails,
        long expiration // Use this parameter instead of the hardcoded 30 hours
    ) {
        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            // Uses the 'expiration' parameter provided to the method
            .expiration(new Date(System.currentTimeMillis() + expiration)) 
            .signWith(getSigningKey(), Jwts.SIG.HS256)
            .compact();
    }
    
    /**
     * Checks if the token has expired.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the token claims.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Parses the token and extracts all claims after signature verification.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    /**
     * Decodes the secret key string into a SecretKey object.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    // --- REMOVED / MERGED METHODS ---
    // The following methods were redundant or incorrectly named and have been removed:
    // 1. public String buildToken1(...) -> Merged logic into the unified private buildToken(...)
    // 2. private String buildToken(...) -> Merged logic into the unified private buildToken(...) and corrected to use 'expiration' parameter.
    
}
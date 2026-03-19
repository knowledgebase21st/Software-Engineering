package jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.nio.charset.StandardCharsets;
public class JwtTokenGenerator {
    /**
     * Generates a signed JWT token using a shared secret key.
     * @param issuer The issuer of the token.
     * @param subject The subject of the token (e.g., user ID).
     * @param secretKey The secret key for signing (must be at least 256 bits).
     * @return The compact JWT string.
     */
    public static String generateJwtToken(String issuer, String subject, String secretKey) {
        // Define the key from the secret string
        // The key should be securely managed and stored
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Instant now = Instant.now();
        
        // Build the JWT
        String jwt = Jwts.builder()
                .setIssuer(issuer) // Set the issuer claim
                .setSubject(subject) // Set the subject claim
                .setIssuedAt(Date.from(now)) // Set the issued at time
                .setExpiration(Date.from(now.plus(5L, ChronoUnit.MINUTES))) // Set expiration for 5 minutes later
                .setId(UUID.randomUUID().toString()) // Set a unique ID for the token
                .signWith(key) // Sign the JWT with the key
                .compact(); // Compacts the JWT into its final string form

        return jwt;
    }

    public static void main(String[] args) {
        // Example usage: replace with your actual values
        String myIssuer = "my.company.com";
        String mySubject = "john.doe";
        // Key must be sufficiently long for HS256 (e.g., 32 characters or more for 256 bits)
        String mySecretKey = "aVerySecureAndLongSecretKeyForSigningJwts"; 

        String token = generateJwtToken(myIssuer, mySubject, mySecretKey);
        System.out.println("Generated JWT Token:");
        System.out.println(token);
    }
}

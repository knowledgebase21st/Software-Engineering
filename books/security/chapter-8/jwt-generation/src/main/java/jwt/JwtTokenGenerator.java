package jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * Demonstrates how to create a signed JSON Web Token (JWT).
 *
 * The token contains standard claims such as issuer, subject,
 * audience, issued-at time, expiration time, and JWT ID.
 *
 * Author: Sanjay Ghosh
 */
public class JwtTokenGenerator {

    /*
     * DEMO ONLY:
     * This secret is included only for learning purposes.
     *
     * Production applications should obtain signing keys from
     * an appropriate secrets or key-management system.
     */
    private static final String DEMO_SECRET_KEY =
            "aVerySecureAndLongSecretKeyForSigningJwts";

    /**
     * Generates a signed JWT using an HMAC signing key.
     *
     * @param issuer    issuer of the token
     * @param subject   subject of the token, such as a user ID
     * @param audience  intended audience of the token
     * @param secretKey secret key used to sign the token
     * @return compact signed JWT
     */
    public static String generateJwtToken(
            String issuer,
            String subject,
            String audience,
            String secretKey) {

        Key key = Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8));

        Instant now = Instant.now();

        String jwt = Jwts.builder()
                .issuer(issuer)
                .subject(subject)
                .audience()
                    .add(audience)
                    .and()
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(5, ChronoUnit.MINUTES)))
                .id(UUID.randomUUID().toString())
                .signWith(key)
                .compact();

        return jwt;
    }

    public static void main(String[] args) {

        String issuer = "my.company.com";
        String subject = "john.doe";
        String audience = "orders-api";

        String token = generateJwtToken(
                issuer,
                subject,
                audience,
                DEMO_SECRET_KEY);

        System.out.println("Generated JWT Token:");
        System.out.println(token);
    }
}

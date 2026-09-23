package jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;

/**
 * Demonstrates validation of a signed JSON Web Token (JWT).
 *
 * The example verifies the JWT signature and validates the expected
 * issuer and audience. The expiration claim is also checked during
 * JWT parsing.
 *
 * Author: Sanjay Ghosh
 */
public class JWTValidation {

    /*
     * DEMO ONLY:
     * This secret must match the signing key used by
     * JwtTokenGenerator.
     *
     * Production applications should obtain signing keys from
     * an appropriate secrets or key-management system.
     */
    private static final String DEMO_SECRET_KEY =
            "aVerySecureAndLongSecretKeyForSigningJwts";

    private static final String EXPECTED_ISSUER =
            "my.company.com";

    private static final String EXPECTED_AUDIENCE =
            "orders-api";

    /**
     * Validates a signed JWT.
     *
     * Validation includes:
     * - Signature verification
     * - Issuer validation
     * - Audience validation
     * - Expiration validation
     *
     * @param token compact JWT string
     * @return validated JWT claims
     */
    public static Claims validate(String token) {

        return Jwts.parser()
                .verifyWith(
                        Keys.hmacShaKeyFor(
                                DEMO_SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .requireIssuer(EXPECTED_ISSUER)
                .requireAudience(EXPECTED_AUDIENCE)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println(
                    "Please provide the JWT token as a parameter.");
            return;
        }

        String token = args[0];

        try {

            Claims claims = validate(token);

            System.out.println("JWT validation successful.");
            System.out.println();

            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issuer: " + claims.getIssuer());
            System.out.println("Audience: " + claims.getAudience());
            System.out.println("Issued At: " + claims.getIssuedAt());
            System.out.println("Expiration: " + claims.getExpiration());
            System.out.println("ID: " + claims.getId());

        } catch (Exception e) {

            System.out.println("JWT validation failed.");
            System.out.println("Reason: " + e.getMessage());
        }
    }
}

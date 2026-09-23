package jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Demonstrates how to decode the header and payload of a JSON Web Token (JWT).
 *
 * This example only decodes the token. It does not verify the signature
 * or validate any JWT claims.
 *
 * Author: Sanjay Ghosh
 */
public class JWTTokenDecoder {

    /**
     * Decodes and displays the header and payload of a JWT.
     *
     * @param token compact JWT string
     */
    public static void decodeJWT(String token) {

        String[] parts = token.split("\\.");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        Base64.Decoder decoder = Base64.getUrlDecoder();

        // Decode Header
        String header = new String(
                decoder.decode(parts[0]),
                StandardCharsets.UTF_8);

        System.out.println("JWT Header:");
        System.out.println(header);

        // Decode Payload
        String payload = new String(
                decoder.decode(parts[1]),
                StandardCharsets.UTF_8);

        System.out.println("JWT Payload:");
        System.out.println(payload);

        /*
         * The third part is the cryptographic signature.
         *
         * It is not decoded into readable JSON like the header
         * and payload. Signature verification requires the appropriate
         * verification key and validation process.
         */
    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println(
                    "Please provide the JWT token as a parameter.");
            return;
        }

        String jwtToken = args[0];

        decodeJWT(jwtToken);
    }
}

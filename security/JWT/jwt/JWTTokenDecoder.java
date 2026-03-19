package jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTTokenDecoder {
    public static void decodeJWT(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        Base64.Decoder decoder = Base64.getUrlDecoder();

        // Decode Header
        String header = new String(decoder.decode(parts[0]), StandardCharsets.UTF_8);
        System.out.println("JWT Header: " + header);

        // Decode Payload (Body)
        String payload = new String(decoder.decode(parts[1]), StandardCharsets.UTF_8);
        System.out.println("JWT Body: " + payload);

        // The signature part (parts[2]) is a hash and cannot be "decoded" into a readable string in the same way.
        // It's used for signature verification.
    }

    public static void main(String[] args) {
	    if (args.length < 1 ) {
		    System.out.println("Please provide the token String as a parameter"); 
		    return;
            }
	    String jwtToken = args[0];
            decodeJWT(jwtToken);
    }
}

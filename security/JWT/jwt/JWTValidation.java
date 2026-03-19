package jwt;
import io.jsonwebtoken.Claims; 
import io.jsonwebtoken.Jwts; 
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;

public class JWTValidation { 
    private static final String SECRET_KEY = "aVerySecureAndLongSecretKeyForSigningJwts"; 
	public static Claims validate(String token) { 
		 return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8))).build().parseSignedClaims(token).getPayload();
	} 

    public static void main(String[] args) {
	    if (args.length < 1 ) {
		    System.out.println("Please provide the token String as a parameter"); 
		    return;
            }
	    String tokenString = args[0];
	    Claims claims = validate (tokenString);
	    System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issuer: " + claims.getIssuer());
            System.out.println("Issued At: " + claims.getIssuedAt());
            System.out.println("Expiration: " + claims.getExpiration());
            System.out.println("ID: " + claims.getId());
            System.out.println("Audience: " + claims.getAudience());
   }


}

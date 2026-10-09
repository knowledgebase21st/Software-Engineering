import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Demonstrates RSA key-pair generation using Java Cryptography Architecture.
 *
 * Author: Sanjay Ghosh
 */
public class RSAKeyGeneration {

    public static void main(String[] args) throws Exception {

        // Create an RSA key-pair generator.
        KeyPairGenerator generator =
                KeyPairGenerator.getInstance("RSA");

        // Use a 2048-bit RSA key for this demonstration.
        generator.initialize(2048);

        // Generate the public/private key pair.
        KeyPair keyPair = generator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        System.out.println("RSA Key Pair Generated Successfully");
        System.out.println("------------------------------------");

        System.out.println("Algorithm: " + publicKey.getAlgorithm());
        System.out.println("Key Size: 2048 bits");

        System.out.println("\nPublic Key:");
        System.out.println(publicKey);

        System.out.println("\nPrivate Key:");
        System.out.println(privateKey);
    }
}

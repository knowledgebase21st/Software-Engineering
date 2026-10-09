import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.Signature;
import java.util.Base64;

/**
 * Demonstrates RSA digital signatures using Java Cryptography Architecture.
 *
 * Author: Sanjay Ghosh
 */
public class RSADigitalSignature {

    public static void main(String[] args) throws Exception {

        // Load the RSA key pair used by the other examples.
        KeyPair keyPair = RSAKeyStore.loadOrCreateKeyPair();

        byte[] message =
                "Practical Application Security"
                        .getBytes(StandardCharsets.UTF_8);

        // Sign the message using the private key.
        Signature signer =
                Signature.getInstance("SHA256withRSA");

        signer.initSign(keyPair.getPrivate());
        signer.update(message);

        byte[] digitalSignature = signer.sign();

        System.out.println("RSA Digital Signature Created");
        System.out.println("-----------------------------");
        System.out.println("Message: "
                + new String(message, StandardCharsets.UTF_8));
        System.out.println("Signature (Base64): "
                + Base64.getEncoder()
                        .encodeToString(digitalSignature));

        // Verify the signature using the public key.
        Signature verifier =
                Signature.getInstance("SHA256withRSA");

        verifier.initVerify(keyPair.getPublic());
        verifier.update(message);

        boolean valid = verifier.verify(digitalSignature);

        System.out.println("\nVerification of Original Message: "
                + valid);

        // Modify the message and attempt verification again.
        byte[] modifiedMessage =
                "Practical Application Security - Modified"
                        .getBytes(StandardCharsets.UTF_8);

        verifier.initVerify(keyPair.getPublic());
        verifier.update(modifiedMessage);

        boolean modifiedValid =
                verifier.verify(digitalSignature);

        System.out.println("Verification of Modified Message: "
                + modifiedValid);
    }
}

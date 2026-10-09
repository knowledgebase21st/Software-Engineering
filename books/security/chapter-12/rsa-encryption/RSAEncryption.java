import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.security.spec.AlgorithmParameterSpec;

/**
 * Demonstrates RSA encryption using Java Cryptography Architecture.
 *
 * Author: Sanjay Ghosh
 */
public class RSAEncryption {

    public static void main(String[] args) throws Exception {

        // Load an existing key pair or generate a new one.
        KeyPair keyPair = RSAKeyStore.loadOrCreateKeyPair();

        String message = "Practical Application Security";
        byte[] plaintext = message.getBytes(
                java.nio.charset.StandardCharsets.UTF_8);

        // Configure RSA-OAEP with SHA-256 and MGF1-SHA-256.
        OAEPParameterSpec oaepParams =
                new OAEPParameterSpec(
                        "SHA-256",
                        "MGF1",
                        MGF1ParameterSpec.SHA256,
                        PSource.PSpecified.DEFAULT);

        Cipher cipher = Cipher.getInstance(
                "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

        cipher.init(
                Cipher.ENCRYPT_MODE,
                keyPair.getPublic(),
                oaepParams);

        byte[] ciphertext = cipher.doFinal(plaintext);

        String encodedCiphertext =
                Base64.getEncoder().encodeToString(ciphertext);

        // Save the ciphertext for the separate decryption program.
        Path ciphertextFile =
                Path.of("demo-keys", "ciphertext.txt");

        Files.writeString(ciphertextFile, encodedCiphertext);

        System.out.println("RSA Encryption Successful");
        System.out.println("-------------------------");
        System.out.println("Original message: " + message);
        System.out.println("Ciphertext (Base64): " + encodedCiphertext);
        System.out.println("Ciphertext saved to: " + ciphertextFile);
    }
}

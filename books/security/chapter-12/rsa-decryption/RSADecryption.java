import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

/**
 * Demonstrates RSA decryption using Java Cryptography Architecture.
 *
 * Author: Sanjay Ghosh
 */
public class RSADecryption {

    public static void main(String[] args) throws Exception {

        // Load the RSA key pair used by the encryption example.
        KeyPair keyPair = RSAKeyStore.loadOrCreateKeyPair();

        // Read the Base64-encoded ciphertext.
        Path ciphertextFile =
                Path.of("demo-keys", "ciphertext.txt");

        String encodedCiphertext =
                Files.readString(ciphertextFile).trim();

        byte[] ciphertext =
                Base64.getDecoder().decode(encodedCiphertext);

        // Use the same OAEP parameters as the encryption program.
        OAEPParameterSpec oaepParams =
                new OAEPParameterSpec(
                        "SHA-256",
                        "MGF1",
                        MGF1ParameterSpec.SHA256,
                        PSource.PSpecified.DEFAULT);

        Cipher cipher = Cipher.getInstance(
                "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

        cipher.init(
                Cipher.DECRYPT_MODE,
                keyPair.getPrivate(),
                oaepParams);

        byte[] plaintext = cipher.doFinal(ciphertext);

        String message = new String(
                plaintext,
                java.nio.charset.StandardCharsets.UTF_8);

        System.out.println("RSA Decryption Successful");
        System.out.println("-------------------------");
        System.out.println("Decrypted message: " + message);
    }
}

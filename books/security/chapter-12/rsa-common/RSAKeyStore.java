import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Generates and stores a demonstration RSA key pair for the
 * encryption and decryption examples.
 *
 * Author: Sanjay Ghosh
 */
public final class RSAKeyStore {

    private static final Path KEY_DIRECTORY =
            Paths.get("demo-keys");

    private static final Path PUBLIC_KEY_FILE =
            KEY_DIRECTORY.resolve("public.key");

    private static final Path PRIVATE_KEY_FILE =
            KEY_DIRECTORY.resolve("private.key");

    private RSAKeyStore() {
    }

    public static KeyPair loadOrCreateKeyPair() throws Exception {

        Files.createDirectories(KEY_DIRECTORY);

        if (Files.exists(PUBLIC_KEY_FILE)
                && Files.exists(PRIVATE_KEY_FILE)) {

            KeyFactory factory = KeyFactory.getInstance("RSA");

            PublicKey publicKey = factory.generatePublic(
                    new X509EncodedKeySpec(
                            Files.readAllBytes(PUBLIC_KEY_FILE)));

            PrivateKey privateKey = factory.generatePrivate(
                    new PKCS8EncodedKeySpec(
                            Files.readAllBytes(PRIVATE_KEY_FILE)));

            return new KeyPair(publicKey, privateKey);
        }

        KeyPairGenerator generator =
                KeyPairGenerator.getInstance("RSA");

        generator.initialize(2048);

        KeyPair keyPair = generator.generateKeyPair();

        Files.write(
                PUBLIC_KEY_FILE,
                keyPair.getPublic().getEncoded());

        Files.write(
                PRIVATE_KEY_FILE,
                keyPair.getPrivate().getEncoded());

        System.out.println("Created a new demonstration RSA key pair.");

        return keyPair;
    }
}

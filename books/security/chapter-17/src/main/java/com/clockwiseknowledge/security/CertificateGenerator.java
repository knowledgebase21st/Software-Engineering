
package com.clockwiseknowledge.security;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.Console;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;

/**
 * Project: Software Engineering Knowledge Base
 * Chapter: 17 - Keystore and Truststore
 * Description: Generates a root CA, intermediate CA, server certificate,
 *              and the corresponding keystores and truststore.
 * Java Version: 17
 *
 * Educational use only. Never use generated CA keys in production.
 */
public class CertificateGenerator {

    private static final String STORE_TYPE = "PKCS12";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final int RSA_KEY_SIZE = 3072;
    private static final int VALIDITY_DAYS = 365;

    private static final SecureRandom RANDOM = new SecureRandom();

    public static void main(String[] args) {
        if (args.length != 0) {
            System.err.println("Usage: CertificateGenerator");
            System.exit(1);
        }

        Console console = System.console();
        if (console == null) {
            System.err.println(
                "No console available. Run this program in a terminal.");
            System.exit(1);
        }

        char[] password = console.readPassword(
            "Password for generated PKCS12 stores: ");

        if (password == null || password.length < 12) {
            System.err.println(
                "Use a password of at least 12 characters.");
            if (password != null) {
                Arrays.fill(password, '\0');
            }
            System.exit(1);
        }

        try {
            generate(password);
        } catch (Exception e) {
            System.err.println(
                "Certificate generation failed: " + e.getMessage());
            System.exit(1);
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    private static void generate(char[] password) throws Exception {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        Path outputDir = Path.of("generated");

        // Avoid overwriting existing keys, certificates, or stores.
        if (Files.exists(outputDir)) {
            try (var files = Files.list(outputDir)) {
                if (files.findAny().isPresent()) {
                    throw new IllegalStateException(
                        "The generated directory is not empty. "
                        + "Inspect or back up its contents, then remove "
                        + "the directory before generating new files.");
                }
            }
        }

        Files.createDirectories(outputDir);

        System.out.println("Generating RSA key pairs...");
        KeyPair rootKeys = generateKeyPair();
        KeyPair intermediateKeys = generateKeyPair();
        KeyPair serverKeys = generateKeyPair();

        System.out.println("Creating root CA certificate...");
        X509Certificate rootCert = createRootCertificate(rootKeys);

        System.out.println("Creating intermediate CA certificate...");
        X509Certificate intermediateCert =
            createIntermediateCertificate(
                rootKeys, rootCert, intermediateKeys);

        System.out.println("Creating server certificate...");
        X509Certificate serverCert =
            createServerCertificate(
                intermediateKeys, intermediateCert, serverKeys);

        // Root CA store: root private key and self-signed certificate.
        savePrivateKeyStore(
            outputDir.resolve("root-ca.p12"),
            "root-ca",
            rootKeys,
            new X509Certificate[]{rootCert},
            password);

        // Intermediate store: intermediate private key and its chain.
        savePrivateKeyStore(
            outputDir.resolve("intermediate-ca.p12"),
            "intermediate-ca",
            intermediateKeys,
            new X509Certificate[]{intermediateCert, rootCert},
            password);

        // Server store: leaf private key and required intermediate chain.
        // The root certificate is intentionally omitted from this chain.
        savePrivateKeyStore(
            outputDir.resolve("server-keystore.p12"),
            "server",
            serverKeys,
            new X509Certificate[]{serverCert, intermediateCert},
            password);

        // Client truststore: root certificate is the trust anchor.
        saveTrustStore(
            outputDir.resolve("client-truststore.p12"),
            "example-root-ca",
            rootCert,
            password);

        writeCertificate(
            outputDir.resolve("root-ca.pem"), rootCert);
        writeCertificate(
            outputDir.resolve("intermediate-ca.pem"), intermediateCert);
        writeCertificate(
            outputDir.resolve("server-cert.pem"), serverCert);

        System.out.println();
        System.out.println("Certificate hierarchy generated successfully.");
        System.out.println("Output directory: " + outputDir.toAbsolutePath());
        System.out.println("Generated files:");
        System.out.println("  root-ca.p12");
        System.out.println("  intermediate-ca.p12");
        System.out.println("  server-keystore.p12");
        System.out.println("  client-truststore.p12");
        System.out.println("  root-ca.pem");
        System.out.println("  intermediate-ca.pem");
        System.out.println("  server-cert.pem");
        System.out.println();
        System.out.println(
            "All PKCS12 stores use the password entered at startup.");
        System.out.println(
            "These generated keys and certificates are for local testing only.");
    }

    private static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(RSA_KEY_SIZE, RANDOM);
        return generator.generateKeyPair();
    }

    private static X509Certificate createRootCertificate(
            KeyPair rootKeys) throws Exception {

        X500Name subject = new X500Name("CN=Chapter 17 Test Root CA");

        JcaX509ExtensionUtils extensions = new JcaX509ExtensionUtils();
        Date notBefore = new Date(System.currentTimeMillis() - 5 * 60 * 1000L);
        Date notAfter = new Date(
            System.currentTimeMillis()
                + VALIDITY_DAYS * 24L * 60 * 60 * 1000);

        JcaX509v3CertificateBuilder builder =
            new JcaX509v3CertificateBuilder(
                subject,
                newSerial(),
                notBefore,
                notAfter,
                subject,
                rootKeys.getPublic());

        builder.addExtension(
            Extension.basicConstraints,
            true,
            new BasicConstraints(1));

        builder.addExtension(
            Extension.keyUsage,
            true,
            new KeyUsage(
                KeyUsage.keyCertSign | KeyUsage.cRLSign));

        builder.addExtension(
            Extension.subjectKeyIdentifier,
            false,
            extensions.createSubjectKeyIdentifier(rootKeys.getPublic()));

        builder.addExtension(
            Extension.authorityKeyIdentifier,
            false,
            extensions.createAuthorityKeyIdentifier(rootKeys.getPublic()));

        return signAndConvert(builder, rootKeys);
    }

    private static X509Certificate createIntermediateCertificate(
            KeyPair rootKeys,
            X509Certificate rootCert,
            KeyPair intermediateKeys) throws Exception {

        X500Name issuer =
            new X500Name(rootCert.getSubjectX500Principal().getName());
        X500Name subject =
            new X500Name("CN=Chapter 17 Test Intermediate CA");

        JcaX509ExtensionUtils extensions = new JcaX509ExtensionUtils();

        JcaX509v3CertificateBuilder builder =
            new JcaX509v3CertificateBuilder(
                issuer,
                newSerial(),
                rootCert.getNotBefore(),
                rootCert.getNotAfter(),
                subject,
                intermediateKeys.getPublic());

        builder.addExtension(
            Extension.basicConstraints,
            true,
            new BasicConstraints(0));

        builder.addExtension(
            Extension.keyUsage,
            true,
            new KeyUsage(
                KeyUsage.keyCertSign | KeyUsage.cRLSign));

        builder.addExtension(
            Extension.subjectKeyIdentifier,
            false,
            extensions.createSubjectKeyIdentifier(
                intermediateKeys.getPublic()));

        builder.addExtension(
            Extension.authorityKeyIdentifier,
            false,
            extensions.createAuthorityKeyIdentifier(
                rootCert.getPublicKey()));

        return signAndConvert(builder, rootKeys);
    }

    private static X509Certificate createServerCertificate(
            KeyPair intermediateKeys,
            X509Certificate intermediateCert,
            KeyPair serverKeys) throws Exception {

        X500Name issuer =
            new X500Name(
                intermediateCert.getSubjectX500Principal().getName());
        X500Name subject = new X500Name("CN=localhost");

        JcaX509ExtensionUtils extensions = new JcaX509ExtensionUtils();

        Date notBefore = new Date(System.currentTimeMillis() - 5 * 60 * 1000L);
        Date notAfter = new Date(
            Math.min(
                intermediateCert.getNotAfter().getTime(),
                System.currentTimeMillis()
                    + VALIDITY_DAYS * 24L * 60 * 60 * 1000));

        JcaX509v3CertificateBuilder builder =
            new JcaX509v3CertificateBuilder(
                issuer,
                newSerial(),
                notBefore,
                notAfter,
                subject,
                serverKeys.getPublic());

        builder.addExtension(
            Extension.basicConstraints,
            true,
            new BasicConstraints(false));

        builder.addExtension(
            Extension.keyUsage,
            true,
            new KeyUsage(
                KeyUsage.digitalSignature | KeyUsage.keyEncipherment));

        builder.addExtension(
            Extension.extendedKeyUsage,
            false,
            new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));

        builder.addExtension(
            Extension.subjectAlternativeName,
            false,
            new GeneralNames(new GeneralName[]{
                new GeneralName(GeneralName.dNSName, "localhost"),
                new GeneralName(GeneralName.iPAddress, "127.0.0.1")
            }));

        builder.addExtension(
            Extension.subjectKeyIdentifier,
            false,
            extensions.createSubjectKeyIdentifier(
                serverKeys.getPublic()));

        builder.addExtension(
            Extension.authorityKeyIdentifier,
            false,
            extensions.createAuthorityKeyIdentifier(
                intermediateCert.getPublicKey()));

        return signAndConvert(builder, intermediateKeys);
    }

    private static X509Certificate signAndConvert(
            JcaX509v3CertificateBuilder builder,
            KeyPair issuerKeys) throws Exception {

        ContentSigner signer =
            new JcaContentSignerBuilder(SIGNATURE_ALGORITHM)
                .setProvider("BC")
                .build(issuerKeys.getPrivate());

        X509CertificateHolder holder = builder.build(signer);

        X509Certificate certificate =
            new JcaX509CertificateConverter()
                .setProvider("BC")
                .getCertificate(holder);

        certificate.verify(issuerKeys.getPublic());
        return certificate;
    }

    private static BigInteger newSerial() {
        return new BigInteger(159, RANDOM).add(BigInteger.ONE);
    }

    private static void savePrivateKeyStore(
            Path path,
            String alias,
            KeyPair keys,
            X509Certificate[] chain,
            char[] password) throws Exception {

        KeyStore store = KeyStore.getInstance(STORE_TYPE);
        store.load(null, password);
        store.setKeyEntry(alias, keys.getPrivate(), password, chain);

        try (OutputStream output = Files.newOutputStream(path)) {
            store.store(output, password);
        }
    }

    private static void saveTrustStore(
            Path path,
            String alias,
            X509Certificate trustedRoot,
            char[] password) throws Exception {

        KeyStore store = KeyStore.getInstance(STORE_TYPE);
        store.load(null, password);
        store.setCertificateEntry(alias, trustedRoot);

        try (OutputStream output = Files.newOutputStream(path)) {
            store.store(output, password);
        }
    }

    private static void writeCertificate(
            Path path, X509Certificate certificate) throws Exception {

        try (JcaPEMWriter writer = new JcaPEMWriter(
                new OutputStreamWriter(
                    Files.newOutputStream(path),
                    StandardCharsets.UTF_8))) {
            writer.writeObject(certificate);
        }
    }
}


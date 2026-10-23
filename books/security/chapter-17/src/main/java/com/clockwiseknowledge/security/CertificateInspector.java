
package com.clockwiseknowledge.security;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;

public class CertificateInspector {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(
                "Usage: CertificateInspector <certificate-file.pem-or-der>"
            );
            System.exit(1);
        }

        Path certificatePath = Path.of(args[0]);

        try {
            X509Certificate certificate = loadCertificate(certificatePath);
            inspectCertificate(certificate);
        } catch (Exception e) {
            System.err.println(
                "Unable to inspect certificate: " + e.getMessage()
            );
            System.exit(1);
        }
    }

    private static X509Certificate loadCertificate(Path path)
            throws Exception {

        CertificateFactory factory =
            CertificateFactory.getInstance("X.509");

        try (InputStream input = Files.newInputStream(path)) {
            return (X509Certificate) factory.generateCertificate(input);
        }
    }

    private static void inspectCertificate(
            X509Certificate certificate) throws Exception {

        System.out.println("=== X.509 Certificate Inspection ===");
        System.out.println("Subject: "
            + certificate.getSubjectX500Principal());
        System.out.println("Issuer: "
            + certificate.getIssuerX500Principal());
        System.out.println("Serial Number: "
            + certificate.getSerialNumber().toString(16));
        System.out.println("Not Before: "
            + certificate.getNotBefore());
        System.out.println("Not After: "
            + certificate.getNotAfter());

        try {
            certificate.checkValidity();
            System.out.println("Date Validity: Currently valid");
        } catch (Exception e) {
            System.out.println("Date Validity: " + e.getMessage());
        }

        PublicKey publicKey = certificate.getPublicKey();
        System.out.println("Public Key Algorithm: "
            + publicKey.getAlgorithm());

        if (publicKey instanceof java.security.interfaces.RSAKey rsaKey) {
            System.out.println("RSA Key Size: "
                + rsaKey.getModulus().bitLength() + " bits");
        } else if (publicKey instanceof
                java.security.interfaces.ECKey ecKey) {
            System.out.println("EC Key Size: "
                + ecKey.getParams().getCurve().getField().getFieldSize()
                + " bits");
        }

        System.out.println("Signature Algorithm: "
            + certificate.getSigAlgName());

        System.out.println("Basic Constraints: "
            + describeBasicConstraints(
                certificate.getBasicConstraints()));

        printSubjectAlternativeNames(certificate);

        System.out.println("==================================");
    }

    private static String describeBasicConstraints(int constraints) {
        if (constraints < 0) {
            return "End-entity certificate (not a CA)";
        }

        return "CA certificate; maximum path length: "
            + (constraints == Integer.MAX_VALUE
                ? "unlimited"
                : constraints);
    }

    private static void printSubjectAlternativeNames(
            X509Certificate certificate) throws Exception {

        System.out.println("Subject Alternative Names:");

        Collection<List<?>> names =
            certificate.getSubjectAlternativeNames();

        if (names == null || names.isEmpty()) {
            System.out.println("  None");
            return;
        }

        for (List<?> name : names) {
            if (name.size() >= 2) {
                Object type = name.get(0);
                Object value = name.get(1);

                String typeName = switch ((Integer) type) {
                    case 2 -> "DNS";
                    case 7 -> "IP";
                    default -> "Type " + type;
                };

                System.out.println("  " + typeName + ": " + value);
            }
        }
    }
}


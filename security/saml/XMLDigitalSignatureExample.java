import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Base64;

/**
 * -----------------------------------------------------------------------------
 * XMLDigitalSignatureExample.java
 * -----------------------------------------------------------------------------
 *
 * Demonstrates how digital signatures work using RSA.
 *
 * In a real SAML authentication flow:
 *
 *      • The Identity Provider (IdP) generates a SAML Assertion.
 *      • The Assertion is digitally signed using the IdP's private key.
 *      • The Service Provider (SP) verifies the signature using the
 *        IdP's public key.
 *
 * This example demonstrates the same concept without using XML Digital
 * Signature (XMLDSig). Instead, it signs a simplified SAML Assertion
 * using Java's built-in Signature API.
 *
 * NOTE:
 * Production SAML implementations use XMLDSig standards and X.509
 * certificates. This example focuses on understanding the core
 * cryptographic principle behind digital signatures.
 *
 * Algorithm:
 *      SHA256withRSA
 *
 * Time Complexity:
 *      Signing      : O(n)
 *      Verification : O(n)
 *
 * -----------------------------------------------------------------------------
 */
public class XMLDigitalSignatureExample {

    /**
     * Program entry point.
     */
    public static void main(String[] args) throws Exception {

        /*
         * Simplified SAML Assertion.
         */
        String samlAssertion = """

<saml:Assertion>

    <saml:Issuer>
        https://login.clockwiseknowledge.com
    </saml:Issuer>

    <saml:Subject>
        <saml:NameID>
            sanjay.ghosh@clockwiseknowledge.com
        </saml:NameID>
    </saml:Subject>

</saml:Assertion>

""";

        /*
         * Generate an RSA public/private key pair.
         *
         * In production, these keys already exist and belong to
         * the Identity Provider.
         */
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048);

        KeyPair keyPair =
                keyPairGenerator.generateKeyPair();

        /*
         * ---------------------------------------------------------
         * Identity Provider
         * Digitally signs the SAML Assertion
         * ---------------------------------------------------------
         */

        Signature signer =
                Signature.getInstance("SHA256withRSA");

        signer.initSign(keyPair.getPrivate());

        signer.update(
                samlAssertion.getBytes(StandardCharsets.UTF_8));

        byte[] digitalSignature =
                signer.sign();

        String encodedSignature =
                Base64.getEncoder().encodeToString(digitalSignature);

        /*
         * ---------------------------------------------------------
         * Service Provider
         * Verifies the digital signature
         * ---------------------------------------------------------
         */

        Signature verifier =
                Signature.getInstance("SHA256withRSA");

        verifier.initVerify(keyPair.getPublic());

        verifier.update(
                samlAssertion.getBytes(StandardCharsets.UTF_8));

        boolean valid =
                verifier.verify(digitalSignature);

        /*
         * Display Results.
         */

        System.out.println("--------------------------------------------");
        System.out.println("Original SAML Assertion");
        System.out.println("--------------------------------------------");

        System.out.println(samlAssertion);

        System.out.println();

        System.out.println("--------------------------------------------");
        System.out.println("Digital Signature (Base64)");
        System.out.println("--------------------------------------------");

        System.out.println(encodedSignature);

        System.out.println();

        System.out.println("--------------------------------------------");
        System.out.println("Signature Verification");
        System.out.println("--------------------------------------------");

        if (valid) {

            System.out.println("Signature is VALID.");
            System.out.println("The SAML Assertion has NOT been modified.");

        }
        else {

            System.out.println("Signature is INVALID.");
            System.out.println("The SAML Assertion may have been tampered with.");

        }

    }

}

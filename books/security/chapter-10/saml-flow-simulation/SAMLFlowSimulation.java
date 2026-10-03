import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.UUID;

import javax.xml.XMLConstants;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class SAMLFlowSimulation {

    // Author: Sanjay Ghosh

    private static final String EXPECTED_ISSUER =
            "https://idp.example.com";

    private static final String EXPECTED_AUDIENCE =
            "https://sp.example.com";

    public static void main(String[] args) throws Exception {

        System.out.println(
                "=== Simplified SAML SSO Flow ===");

        runScenario(
                "Scenario 1: Valid Assertion",
                EXPECTED_ISSUER,
                EXPECTED_AUDIENCE,
                5);

        runScenario(
                "Scenario 2: Invalid Audience",
                EXPECTED_ISSUER,
                "https://wrong-sp.example.com",
                5);

        runScenario(
                "Scenario 3: Expired Assertion",
                EXPECTED_ISSUER,
                EXPECTED_AUDIENCE,
                -5);
    }

    /**
     * Runs one complete SAML SSO scenario.
     *
     * @param scenarioName name displayed for the scenario
     * @param issuer issuer placed in the assertion
     * @param audience audience placed in the assertion
     * @param validityMinutes number of minutes for the assertion
     */
    private static void runScenario(
            String scenarioName,
            String issuer,
            String audience,
            int validityMinutes) throws Exception {

        String user =
                "john.doe@example.com";

        System.out.println();
        System.out.println(
                "========================================");

        System.out.println(
                scenarioName);

        System.out.println(
                "========================================");

        /*
         * 1. User requests access to the Service Provider.
         */
        System.out.println(
                "\n1. User requests access to the Service Provider.");

        System.out.println(
                "   User: " + user);

        /*
         * 2. Service Provider redirects the user
         *    to the Identity Provider.
         */
        System.out.println(
                "\n2. Service Provider redirects the user " +
                "to the Identity Provider.");

        System.out.println(
                "   IdP: " + issuer);

        /*
         * 3. Identity Provider authenticates the user.
         */
        System.out.println(
                "\n3. Identity Provider authenticates the user.");

        System.out.println(
                "   Authentication successful.");

        /*
         * 4. Identity Provider creates a SAML assertion.
         */
        System.out.println(
                "\n4. Identity Provider creates a SAML assertion.");

        Document document =
                createSAMLAssertion(
                        user,
                        issuer,
                        audience,
                        validityMinutes);

        String assertionId =
                document.getDocumentElement()
                        .getAttribute("ID");

        System.out.println(
                "   Assertion ID: " + assertionId);

        /*
         * 5. Identity Provider signs the assertion.
         */
        System.out.println(
                "\n5. Identity Provider signs the SAML assertion.");

        KeyPair keyPair =
                generateKeyPair();

        signAssertion(
                document,
                keyPair);

        System.out.println(
                "   Signature algorithm: RSA-SHA256");

        /*
         * 6. Identity Provider returns the SAML response
         *    to the Service Provider.
         */
        System.out.println(
                "\n6. Identity Provider returns the SAML " +
                "response to the Service Provider.");

        /*
         * 7. Service Provider validates the assertion.
         */
        System.out.println(
                "\n7. Service Provider validates the assertion.");

        boolean signatureValid =
                validateSignature(
                        document,
                        keyPair);

        boolean issuerValid =
                validateIssuer(
                        document);

        boolean audienceValid =
                validateAudience(
                        document);

        boolean validityPeriodValid =
                validateValidityPeriod(
                        document);

        System.out.println(
                "   Signature: " +
                (signatureValid
                        ? "VALID"
                        : "INVALID"));

        System.out.println(
                "   Issuer: " +
                (issuerValid
                        ? "VALID"
                        : "INVALID"));

        System.out.println(
                "   Audience: " +
                (audienceValid
                        ? "VALID"
                        : "INVALID"));

        System.out.println(
                "   Validity period: " +
                (validityPeriodValid
                        ? "VALID"
                        : "INVALID"));

        /*
         * Access is granted only when every required
         * validation check succeeds.
         */
        boolean accessGranted =
                signatureValid
                        && issuerValid
                        && audienceValid
                        && validityPeriodValid;

        System.out.println();

        if (accessGranted) {

            System.out.println(
                    "8. Service Provider establishes " +
                    "an application session.");

            System.out.println(
                    "   Access granted to: " + user);

        } else {

            System.out.println(
                    "8. Service Provider rejects the assertion.");

            System.out.println(
                    "   Access denied.");
        }
    }

    /**
     * Creates a simplified SAML assertion.
     */
    private static Document createSAMLAssertion(
            String user,
            String issuerValue,
            String audienceValue,
            int validityMinutes) throws Exception {

        final String SAML_NAMESPACE =
                "urn:oasis:names:tc:SAML:2.0:assertion";

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);

        factory.setFeature(
                XMLConstants.FEATURE_SECURE_PROCESSING,
                true);

        factory.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl",
                true);

        factory.setFeature(
                "http://xml.org/sax/features/external-general-entities",
                false);

        factory.setFeature(
                "http://xml.org/sax/features/external-parameter-entities",
                false);

        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        Document document =
                factory.newDocumentBuilder()
                        .newDocument();

        Instant now =
                Instant.now()
                        .truncatedTo(ChronoUnit.SECONDS);

        /*
         * For the normal scenario:
         *
         * NotBefore = current time
         * NotOnOrAfter = current time + 5 minutes
         *
         * For the expired scenario:
         *
         * NotBefore = current time - 10 minutes
         * NotOnOrAfter = current time - 5 minutes
         */
        Instant notBefore;

        Instant notOnOrAfter;

        if (validityMinutes >= 0) {

            notBefore = now;

            notOnOrAfter =
                    now.plus(
                            validityMinutes,
                            ChronoUnit.MINUTES);

        } else {

            notBefore =
                    now.plus(
                            validityMinutes * 2L,
                            ChronoUnit.MINUTES);

            notOnOrAfter =
                    now.plus(
                            validityMinutes,
                            ChronoUnit.MINUTES);
        }

        /*
         * Assertion
         */
        var assertion =
                document.createElementNS(
                        SAML_NAMESPACE,
                        "saml:Assertion");

        assertion.setAttribute(
                "ID",
                "_" + UUID.randomUUID());

        assertion.setAttribute(
                "Version",
                "2.0");

        /*
         * Issuer
         */
        var issuer =
                document.createElementNS(
                        SAML_NAMESPACE,
                        "saml:Issuer");

        issuer.setTextContent(
                issuerValue);

        /*
         * Subject
         */
        var subject =
                document.createElementNS(
                        SAML_NAMESPACE,
                        "saml:Subject");

        var nameId =
                document.createElementNS(
                        SAML_NAMESPACE,
                        "saml:NameID");

        nameId.setTextContent(user);

        subject.appendChild(nameId);

        /*
         * Conditions
         */
        var conditions =
                document.createElementNS(
                        SAML_NAMESPACE,
                        "saml:Conditions");

        conditions.setAttribute(
                "NotBefore",
                notBefore.toString());

        conditions.setAttribute(
                "NotOnOrAfter",
                notOnOrAfter.toString());

        /*
         * Audience restriction
         */
        var audienceRestriction =
                document.createElementNS(
                        SAML_NAMESPACE,
                        "saml:AudienceRestriction");

        var audience =
                document.createElementNS(
                        SAML_NAMESPACE,
                        "saml:Audience");

        audience.setTextContent(
                audienceValue);

        audienceRestriction.appendChild(
                audience);

        conditions.appendChild(
                audienceRestriction);

        /*
         * Authentication statement
         */
        var authnStatement =
                document.createElementNS(
                        SAML_NAMESPACE,
                        "saml:AuthnStatement");

        authnStatement.setAttribute(
                "AuthnInstant",
                now.toString());

        /*
         * Build the assertion.
         */
        assertion.appendChild(issuer);
        assertion.appendChild(subject);
        assertion.appendChild(conditions);
        assertion.appendChild(authnStatement);

        document.appendChild(assertion);

        /*
         * Declare the SAML namespace on the root element.
         */
        assertion.setAttributeNS(
                XMLConstants.XMLNS_ATTRIBUTE_NS_URI,
                "xmlns:saml",
                SAML_NAMESPACE);

        return document;
    }

    /**
     * Generates an RSA key pair for this demonstration.
     */
    private static KeyPair generateKeyPair()
            throws Exception {

        KeyPairGenerator generator =
                KeyPairGenerator.getInstance("RSA");

        generator.initialize(2048);

        return generator.generateKeyPair();
    }

    /**
     * Signs the SAML assertion using RSA-SHA256.
     */
    private static void signAssertion(
            Document document,
            KeyPair keyPair) throws Exception {

        XMLSignatureFactory factory =
                XMLSignatureFactory.getInstance("DOM");

        var reference =
                factory.newReference(
                        "",
                        factory.newDigestMethod(
                                "http://www.w3.org/2001/04/xmlenc#sha256",
                                null),
                        Collections.singletonList(
                                factory.newTransform(
                                        "http://www.w3.org/2000/09/xmldsig#enveloped-signature",
                                        (TransformParameterSpec) null)),
                        null,
                        null);

        var signedInfo =
                factory.newSignedInfo(
                        factory.newCanonicalizationMethod(
                                "http://www.w3.org/2001/10/xml-exc-c14n#",
                                (C14NMethodParameterSpec) null),

                        factory.newSignatureMethod(
                                "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256",
                                null),

                        Collections.singletonList(reference));

        KeyInfoFactory keyInfoFactory =
                factory.getKeyInfoFactory();

        KeyValue keyValue =
                keyInfoFactory.newKeyValue(
                        keyPair.getPublic());

        KeyInfo keyInfo =
                keyInfoFactory.newKeyInfo(
                        Collections.singletonList(keyValue));

        DOMSignContext signContext =
                new DOMSignContext(
                        keyPair.getPrivate(),
                        document.getDocumentElement());

        XMLSignature signature =
                factory.newXMLSignature(
                        signedInfo,
                        keyInfo);

        signature.sign(signContext);
    }

    /**
     * Validates the XML digital signature.
     */
    private static boolean validateSignature(
            Document document,
            KeyPair keyPair) throws Exception {

        XMLSignatureFactory factory =
                XMLSignatureFactory.getInstance("DOM");

        Node signatureNode =
                document
                        .getElementsByTagNameNS(
                                XMLSignature.XMLNS,
                                "Signature")
                        .item(0);

        if (signatureNode == null) {
            return false;
        }

        DOMValidateContext validateContext =
                new DOMValidateContext(
                        keyPair.getPublic(),
                        signatureNode);

        XMLSignature signature =
                factory.unmarshalXMLSignature(
                        validateContext);

        return signature.validate(
                validateContext);
    }

    /**
     * Validates the SAML issuer.
     */
    private static boolean validateIssuer(
            Document document) {

        final String SAML_NAMESPACE =
                "urn:oasis:names:tc:SAML:2.0:assertion";

        Node issuer =
                document
                        .getElementsByTagNameNS(
                                SAML_NAMESPACE,
                                "Issuer")
                        .item(0);

        if (issuer == null) {
            return false;
        }

        return EXPECTED_ISSUER.equals(
                issuer.getTextContent().trim());
    }

    /**
     * Validates the SAML audience.
     */
    private static boolean validateAudience(
            Document document) {

        final String SAML_NAMESPACE =
                "urn:oasis:names:tc:SAML:2.0:assertion";

        Node audience =
                document
                        .getElementsByTagNameNS(
                                SAML_NAMESPACE,
                                "Audience")
                        .item(0);

        if (audience == null) {
            return false;
        }

        return EXPECTED_AUDIENCE.equals(
                audience.getTextContent().trim());
    }

    /**
     * Validates the SAML assertion time conditions.
     */
    private static boolean validateValidityPeriod(
            Document document) {

        final String SAML_NAMESPACE =
                "urn:oasis:names:tc:SAML:2.0:assertion";

        Node conditions =
                document
                        .getElementsByTagNameNS(
                                SAML_NAMESPACE,
                                "Conditions")
                        .item(0);

        if (conditions == null) {
            return false;
        }

        Node notBeforeAttribute =
                conditions.getAttributes()
                        .getNamedItem("NotBefore");

        Node notOnOrAfterAttribute =
                conditions.getAttributes()
                        .getNamedItem("NotOnOrAfter");

        if (notBeforeAttribute == null
                || notOnOrAfterAttribute == null) {

            return false;
        }

        Instant notBefore =
                Instant.parse(
                        notBeforeAttribute.getNodeValue());

        Instant notOnOrAfter =
                Instant.parse(
                        notOnOrAfterAttribute.getNodeValue());

        Instant now =
                Instant.now();

        return !now.isBefore(notBefore)
                && now.isBefore(notOnOrAfter);
    }
}

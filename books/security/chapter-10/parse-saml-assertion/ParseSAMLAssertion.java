import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ParseSAMLAssertion {

    // Author: Sanjay Ghosh

    private static final String SAMPLE_ASSERTION = """
            <saml:Assertion
                xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion"
                ID="_abc123"
                Version="2.0">

                <saml:Issuer>
                    https://idp.example.com
                </saml:Issuer>

                <saml:Subject>
                    <saml:NameID>
                        john.doe@example.com
                    </saml:NameID>
                </saml:Subject>

                <saml:AttributeStatement>

                    <saml:Attribute Name="email">
                        <saml:AttributeValue>
                            john.doe@example.com
                        </saml:AttributeValue>
                    </saml:Attribute>

                    <saml:Attribute Name="department">
                        <saml:AttributeValue>
                            Engineering
                        </saml:AttributeValue>
                    </saml:Attribute>

                </saml:AttributeStatement>

            </saml:Assertion>
            """;

    public static void main(String[] args) throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);

        // Basic XML parser hardening for untrusted XML.
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
                factory.newDocumentBuilder().parse(
                        new ByteArrayInputStream(
                                SAMPLE_ASSERTION.getBytes(
                                        StandardCharsets.UTF_8)));

        String namespace =
                "urn:oasis:names:tc:SAML:2.0:assertion";

        System.out.println("=== Parsed SAML Assertion ===");

        System.out.println(
                "Issuer: " +
                document
                        .getElementsByTagNameNS(namespace, "Issuer")
                        .item(0)
                        .getTextContent()
                        .trim());

        System.out.println(
                "Subject: " +
                document
                        .getElementsByTagNameNS(namespace, "NameID")
                        .item(0)
                        .getTextContent()
                        .trim());

        NodeList attributes =
                document.getElementsByTagNameNS(
                        namespace,
                        "Attribute");

        for (int i = 0; i < attributes.getLength(); i++) {

            var attribute = attributes.item(i);

            String name =
                    attribute.getAttributes()
                            .getNamedItem("Name")
                            .getNodeValue();

            String value =
                    ((org.w3c.dom.Element) attribute)
                            .getElementsByTagNameNS(
                                    namespace,
                                    "AttributeValue")
                            .item(0)
                            .getTextContent()
                            .trim();

            System.out.println(name + ": " + value);
        }
    }
}

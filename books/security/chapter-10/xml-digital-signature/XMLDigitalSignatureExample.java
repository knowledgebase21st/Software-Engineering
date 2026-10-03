import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Collections;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import org.xml.sax.InputSource;

public class XMLDigitalSignatureExample {

    // Author: Sanjay Ghosh
    public static void main(String[] args) throws Exception {
    
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");
    
        keyPairGenerator.initialize(2048);
    
        KeyPair keyPair =
                keyPairGenerator.generateKeyPair();
    
        Document document = createDocument();
    
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
    
        /*
         * Validate the signature against the same DOM that
         * was signed. This avoids introducing formatting
         * whitespace between signing and validation.
         */
        Node signatureNode =
                document
                        .getElementsByTagNameNS(
                                XMLSignature.XMLNS,
                                "Signature")
                        .item(0);
    
        DOMValidateContext validateContext =
                new DOMValidateContext(
                        keyPair.getPublic(),
                        signatureNode);
    
        XMLSignature validationSignature =
                factory.unmarshalXMLSignature(
                        validateContext);
    
        boolean valid =
                validationSignature.validate(
                        validateContext);
    
        System.out.println(
                "Signature valid: " + valid);
    
        System.out.println("\n=== Signed XML ===");
        System.out.println(toXml(document));
    }

    private static Document createDocument()
            throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);

        factory.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl",
                true);

        factory.setFeature(
                "http://xml.org/sax/features/external-general-entities",
                false);

        factory.setFeature(
                "http://xml.org/sax/features/external-parameter-entities",
                false);

        Document document =
                factory.newDocumentBuilder()
                        .newDocument();

        var root =
                document.createElement(
                        "saml:Assertion");

        root.setAttribute(
                "xmlns:saml",
                "urn:oasis:names:tc:SAML:2.0:assertion");

        root.setAttribute(
                "ID",
                "_abc123");

        root.setAttribute(
                "Version",
                "2.0");

        var issuer =
                document.createElement(
                        "saml:Issuer");

        issuer.setTextContent(
                "https://idp.example.com");

        var subject =
                document.createElement(
                        "saml:Subject");

        var nameId =
                document.createElement(
                        "saml:NameID");

        nameId.setTextContent(
                "john.doe@example.com");

        subject.appendChild(nameId);

        root.appendChild(issuer);
        root.appendChild(subject);

        document.appendChild(root);

        return document;
    }

    private static Document parseXml(
            String xml) throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);

        factory.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl",
                true);

        factory.setFeature(
                "http://xml.org/sax/features/external-general-entities",
                false);

        factory.setFeature(
                "http://xml.org/sax/features/external-parameter-entities",
                false);

        return factory.newDocumentBuilder()
                .parse(
                        new InputSource(
                                new StringReader(xml)));
    }

    private static String toXml(
            Document document) throws Exception {

        Transformer transformer =
                TransformerFactory
                        .newInstance()
                        .newTransformer();

        transformer.setOutputProperty(
                OutputKeys.INDENT,
                "yes");

        StringWriter writer =
                new StringWriter();

        transformer.transform(
                new DOMSource(document),
                new StreamResult(writer));

        return writer.toString();
    }
}

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SAMLBase64Encoding {

    // Author: Sanjay Ghosh

    public static void main(String[] args) {

        String samlXml = """
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

                </saml:Assertion>
                """.trim();

        String encoded =
                Base64.getEncoder()
                        .encodeToString(
                                samlXml.getBytes(
                                        StandardCharsets.UTF_8));

        String decoded =
                new String(
                        Base64.getDecoder().decode(encoded),
                        StandardCharsets.UTF_8);

        System.out.println("=== Base64 Encoding ===");

        System.out.println("\nOriginal SAML XML:");
        System.out.println(samlXml);

        System.out.println("\nBase64 encoded value:");
        System.out.println(encoded);

        System.out.println("\nDecoded SAML XML:");
        System.out.println(decoded);

        System.out.println(
                "\nBase64 provides encoding, not encryption.");
    }
}

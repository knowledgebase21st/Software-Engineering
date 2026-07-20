import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * -----------------------------------------------------------------------------
 * SAMLBase64Encoding.java
 * -----------------------------------------------------------------------------
 *
 * Demonstrates how a SAML Assertion is Base64 encoded and decoded.
 *
 * In a typical SAML authentication flow:
 *
 *      1. The Identity Provider (IdP) generates a SAML Assertion.
 *      2. The XML Assertion is Base64 encoded.
 *      3. The encoded string is sent to the user's browser.
 *      4. The browser forwards it to the Service Provider (SP).
 *      5. The Service Provider decodes the Base64 string,
 *         verifies the digital signature, and authenticates the user.
 *
 * Base64 encoding does NOT encrypt the SAML Assertion.
 * It simply converts the XML document into a text-safe format suitable
 * for transmission over HTTP.
 *
 * Time Complexity:
 *      Encoding : O(n)
 *      Decoding : O(n)
 *
 * -----------------------------------------------------------------------------
 */
public class SAMLBase64Encoding {

    /**
     * Program entry point.
     */
    public static void main(String[] args) {

        /*
         * Sample SAML Assertion.
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

    <saml:AttributeStatement>

        <Email>

            sanjay.ghosh@clockwiseknowledge.com

        </Email>

        <Department>

            Engineering

        </Department>

        <Role>

            Administrator

        </Role>

    </saml:AttributeStatement>

</saml:Assertion>

""";

        /*
         * Encode the SAML Assertion using Base64.
         */
        String encodedAssertion =
                Base64.getEncoder().encodeToString(
                        samlAssertion.getBytes(StandardCharsets.UTF_8));

        System.out.println("--------------------------------------------");
        System.out.println("Base64 Encoded SAML Assertion");
        System.out.println("--------------------------------------------");

        System.out.println(encodedAssertion);

        /*
         * Decode the Base64 encoded SAML Assertion.
         */
        String decodedAssertion =
                new String(
                        Base64.getDecoder().decode(encodedAssertion),
                        StandardCharsets.UTF_8);

        System.out.println();

        System.out.println("--------------------------------------------");
        System.out.println("Decoded SAML Assertion");
        System.out.println("--------------------------------------------");

        System.out.println(decodedAssertion);

    }
}

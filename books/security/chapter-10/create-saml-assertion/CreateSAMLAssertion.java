import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class CreateSAMLAssertion {

    // Author: Sanjay Ghosh

    public static String createAssertion() {
        String issueInstant =
                Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();

        String notOnOrAfter =
                Instant.now()
                        .plus(5, ChronoUnit.MINUTES)
                        .truncatedTo(ChronoUnit.SECONDS)
                        .toString();

        return """
                <saml:Assertion
                    xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion"
                    ID="_abc123"
                    Version="2.0">

                    <saml:Issuer>https://idp.example.com</saml:Issuer>

                    <saml:Subject>
                        <saml:NameID>john.doe@example.com</saml:NameID>
                    </saml:Subject>

                    <saml:Conditions
                        NotBefore="%s"
                        NotOnOrAfter="%s">

                        <saml:AudienceRestriction>
                            <saml:Audience>
                                https://sp.example.com
                            </saml:Audience>
                        </saml:AudienceRestriction>

                    </saml:Conditions>

                    <saml:AuthnStatement
                        AuthnInstant="%s">

                        <saml:AuthnContext>
                            <saml:AuthnContextClassRef>
                                urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport
                            </saml:AuthnContextClassRef>
                        </saml:AuthnContext>

                    </saml:AuthnStatement>

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
                """.formatted(
                issueInstant,
                notOnOrAfter,
                issueInstant
        ).trim();
    }

    public static void main(String[] args) {
        System.out.println("=== Simplified SAML Assertion ===");
        System.out.println(createAssertion());
    }
}

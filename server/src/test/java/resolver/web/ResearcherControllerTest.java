package resolver.web;

import io.restassured.http.Header;
import org.junit.Test;
import resolver.AbstractIntegrationTest;
import resolver.model.EmployeeType;
import resolver.model.Identity;
import resolver.model.IdentityType;
import resolver.model.Researcher;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class ResearcherControllerTest extends AbstractIntegrationTest {

    @Test
    public void researchers() {
        given()
            .auth().preemptive().basic("user", "secret")
            .when()
            .get("api/resolver/researchers")
            .then()
            .statusCode(SC_OK)
            .body("id", hasItems(3));
    }

    @Test
    public void researchersWrongAuth() throws Exception {
        given()
            .auth().preemptive().basic("nope", "secret")
            .when()
            .get("api/resolver/researchers")
            .then()
            .statusCode(SC_FORBIDDEN);
    }

    @Test
    public void newResearcher() throws Exception {

        Researcher researcher = new Researcher(Identity.identities(Collections.singletonList("https://orcid" +
            ".org/0000-0002-3843-3473"), Collections.singletonList(IdentityType.ORCID)), "dummy.org", "sam.doe",
            EmployeeType.CURRENT, Boolean.TRUE, "Sam", "sam@dummy.org");
        given()
            .auth().preemptive().basic("user", "secret")
            .header(new Header("Content-type", "application/json"))
            .body(researcher)
            .when()
            .post("api/resolver/researchers")
            .then()
            .body(isEmptyOrNullString());


    }

}

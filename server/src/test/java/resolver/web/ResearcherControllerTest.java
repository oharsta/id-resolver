package resolver.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import org.junit.Test;
import resolver.AbstractIntegrationTest;
import resolver.model.EmployeeType;
import resolver.model.Identity;
import resolver.model.IdentityType;
import resolver.model.Researcher;
import resolver.model.ResearcherView;

import static io.restassured.RestAssured.given;
import static java.util.Collections.singletonList;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

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
    public void researcherById() {
        given()
            .auth().preemptive().basic("user", "secret")
            .when()
            .get("api/resolver/researchers/1")
            .then()
            .statusCode(SC_OK)
            .body("parents.size()", equalTo(1))
            .body("children.size()", equalTo(2))
            .body("authorships.size()", equalTo(2))
            .body("id", equalTo(1));
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
        Researcher researcher = new Researcher(Identity.identities(singletonList("https://orcid" +
            ".org/0000-0002-3843-3473"), singletonList(IdentityType.ORCID)), "example.org", "sam.doe",
            EmployeeType.CURRENT, Boolean.TRUE, "Sam", "sam@dummy.org");

        given()
            .auth().preemptive().basic("user", "secret")
            .header(new Header("Content-type", "application/json"))
            .body(researcher)
            .when()
            .post("api/resolver/researchers")
            .then()
            .body("id", notNullValue())
            .body("identities.size()", equalTo(1))
            .body("parents.size()", equalTo(0))
            .body("children.size()", equalTo(1))
            .body("children[0].weight", equalTo(100));
    }

    @Test
    public void newResearcherNotAllowed() throws Exception {
        Researcher researcher = new Researcher(Identity.identities(
            singletonList("https://orcid.org/0000-0002-3843-3473"),
            singletonList(IdentityType.ORCID)), "dummy.org", "sam.doe",
            EmployeeType.CURRENT, Boolean.TRUE, "Sam", "sam@dummy.org");

        given()
            .auth().preemptive().basic("user", "secret")
            .header(new Header("Content-type", "application/json"))
            .body(researcher)
            .when()
            .post("api/resolver/researchers")
            .then()
            .statusCode(403);
    }

    @Test
    public void updateResearcher() throws Exception {
        Researcher researcher = new Researcher(Identity.identities(singletonList("987654321"),
            singletonList(IdentityType.SCOPUS)), "example.org", "sam.doe",
            EmployeeType.CURRENT, Boolean.TRUE, "Mary2", "mary.doe@example.org");

        System.out.println(new ObjectMapper().writeValueAsString(researcher));;

        ResearcherView researcherView = given()
            .auth().preemptive().basic("user", "secret")
            .header(new Header("Content-type", "application/json"))
            .body(researcher)
            .when()
            .post("api/resolver/researchers")
            .as(ResearcherView.class);
        assertEquals(1, researcherView.getChildren().size());
        assertEquals(new Integer(50), researcherView.getChildren().iterator().next().getWeight());

        //Now change the email, verify the update and ensure there are no more children
        researcher.setEmail("changed@org.com");
        researcher.setId(researcherView.getId());
        researcherView = given()
            .auth().preemptive().basic("user", "secret")
            .header(new Header("Content-type", "application/json"))
            .body(researcher)
            .when()
            .put("api/resolver/researchers")
            .as(ResearcherView.class);
        assertEquals(0, researcherView.getChildren().size());
        assertEquals("changed@org.com", researcherView.getEmail());
    }

}

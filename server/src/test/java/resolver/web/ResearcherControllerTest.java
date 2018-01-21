package resolver.web;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import resolver.AbstractIntegrationTest;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.context.jdbc.SqlConfig.ErrorMode.FAIL_ON_ERROR;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class ResearcherControllerTest extends AbstractIntegrationTest {

    @Test
    public void researchers() {
        given()
            .auth().preemptive().basic("user", "secret")
            .when()
            .get("resolver/api/researchers/")
            .then()
            .statusCode(SC_OK)
            .body("id", hasItems(3));
    }

    @Test
    public void researchersWrongAuth() throws Exception {
        given()
            .auth().preemptive().basic("nope", "secret")
            .when()
            .get("resolver/api/researchers/")
            .then()
            .statusCode(SC_FORBIDDEN);
    }

}

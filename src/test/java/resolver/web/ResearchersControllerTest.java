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

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.context.jdbc.SqlConfig.ErrorMode.FAIL_ON_ERROR;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Sql(scripts = {"classpath:sql/clear.sql", "classpath:sql/seed.sql"},
    config = @SqlConfig(errorMode = FAIL_ON_ERROR, transactionMode = ISOLATED))
public class ResearchersControllerTest {

    @LocalServerPort
    protected int port;

    @Before
    public void before() {
        RestAssured.port = port;
    }

    @Test
    public void researchers() {
        given()
            .auth().preemptive().basic("user", "secret")
            .when()
            .get("researchers/")
            .then()
            .statusCode(SC_OK)
            .body("id", hasItems(3));
    }

    @Test
    public void researchersWrongAuth() throws Exception {
        given()
            .auth().preemptive().basic("nope", "secret")
            .when()
            .get("researchers/")
            .then()
            .statusCode(SC_FORBIDDEN);
    }

    @Test
    public void encryptPassword() {
        given()
            .when()
            .get("users/encodePassword/secret")
            .then()
            .body(startsWith("{bcrypt}$2a$10$"));
    }
}

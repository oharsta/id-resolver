package resolver.web;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.context.jdbc.SqlConfig.ErrorMode.FAIL_ON_ERROR;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    protected int port;

    @Before
    public void before() {
        RestAssured.port = port;
    }

    @Test
    public void encryptPassword() {
        given()
            .when()
            .get("client/users/encodePassword/secret")
            .then()
            .body(startsWith("{bcrypt}$2a$10$"));
    }

    @Test
    public void researchers() {
        given()
            .auth().preemptive().basic("user", "secret")
            .when()
            .get("client/users/me")
            .then()
            .statusCode(SC_OK)
            .body("name", equalTo("user"));
    }

    @Test
    public void config() {
        given()
            .when()
            .get("client/users/config")
            .then()
            .statusCode(SC_OK);
    }

    @Test
    public void error() {
        given()
            .when()
            .header(new Header("Content-type", "application/json"))
            .body(Collections.singletonMap("error", "damn"))
            .post("client/users/error")
            .then()
            .statusCode(SC_OK);
    }
}

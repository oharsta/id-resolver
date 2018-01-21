package resolver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @LocalServerPort
    private int port;

    @Test
    public void startUp() {
        ResponseEntity<Map> response = new RestTemplate().getForEntity("http://localhost:" + port + "/actuator/health",
            Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().get("status"), "UP");
    }

    @Test
    public void testMain() {
        Application application = new Application();
        application.main(new String[]{});
    }

}
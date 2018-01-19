package resolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.trace.TraceWebFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {TraceWebFilterAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

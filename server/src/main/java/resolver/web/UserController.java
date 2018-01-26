package resolver.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resolver.api.APIUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = {"/api/resolver", "/client"})
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @GetMapping("/users/encodePassword/{rawPassword}")
    public String encodePassword(@PathVariable("rawPassword") String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @GetMapping("/users/me")
    public APIUser me(APIUser apiUser) {
        return apiUser.clearCredentials();
    }

    @PostMapping("/users/error")
    public void error(@RequestBody Object o) {
        LOG.error(o.toString());
    }


    @GetMapping("/users/config")
    public Map<String, List<String>> config() {
        return Collections.singletonMap("environments", Arrays.asList(environment.getActiveProfiles()));
    }

}

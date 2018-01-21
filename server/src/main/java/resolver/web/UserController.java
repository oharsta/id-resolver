package resolver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import resolver.api.APIUser;
import resolver.model.Researcher;
import resolver.repository.ResearcherRepository;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users/encodePassword/{rawPassword}")
    public String encodePassword(@PathVariable("rawPassword") String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}

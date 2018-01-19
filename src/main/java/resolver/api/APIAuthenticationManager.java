package resolver.api;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.util.stream.Collectors.toList;

public class APIAuthenticationManager implements AuthenticationManager {

    private APIUserConfiguration apiUserConfiguration;

    private PasswordEncoder passwordEncoder;

    public APIAuthenticationManager(APIUserConfiguration apiUserConfiguration, PasswordEncoder passwordEncoder) {
        this.apiUserConfiguration = apiUserConfiguration;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String name = String.class.cast(auth.getPrincipal());
        return apiUserConfiguration.getApiUsers().stream()
            .filter(apiUser -> apiUser.getName().equals(name))
            .findFirst()
            .filter(apiUser -> passwordEncoder.matches((String) auth.getCredentials(), apiUser.getPassword()))
            .map(apiUser -> new UsernamePasswordAuthenticationToken(
                apiUser,
                auth.getCredentials(),
                apiUser.getScopes().stream().map(scope -> new SimpleGrantedAuthority("ROLE_".concat(scope.name())))
                    .collect(toList())))
            .orElseThrow(() -> new AuthenticationServiceException("Nope"));
    }
}

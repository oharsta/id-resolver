package resolver.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIUser {

    private String name;
    private String password;
    private String organisation;
    private List<Scope> scopes;


    public APIUser clearCredentials() {
        return new APIUser(name, null,organisation, scopes);
    }
}

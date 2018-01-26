package resolver.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ResearcherRelationView {

    private Long id;
    private String name;
    private String email;
    private String organisation;
    private String organisationUid;
    private EmployeeType employeeType;
    private Set<Identity> identities;
    private Boolean authoritative;
    private Integer weight;

    public ResearcherRelationView(Researcher researcher, Integer weight) {
        this.id = researcher.getId();
        this.name = researcher.getName();
        this.email = researcher.getEmail();
        this.organisation = researcher.getOrganisation();
        this.organisationUid = researcher.getOrganisationUid();
        this.employeeType = researcher.getEmployeeType();
        this.identities = researcher.getIdentities();
        this.authoritative = researcher.getAuthoritative();
        this.weight = weight;

    }
}

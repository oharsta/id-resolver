package resolver.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "researchers")
@Getter
@NoArgsConstructor
@Setter
@EqualsAndHashCode(of = "id")
public class Researcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "researcher", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotNull
    @Size(min = 1)
    private Set<Identity> identities = new HashSet<>();

    @OneToMany(mappedBy = "child")
    private Set<ResearcherParent> parents = new HashSet<>();

    @OneToMany(mappedBy = "parent")
    private Set<ResearcherChild> children = new HashSet<>();

    @Column
    @NotNull
    private String organisation;

    @Column
    @NotNull
    private String organisationUid;

    @OneToMany(mappedBy = "researcher")
    private Set<Authorship> authorships = new HashSet<>();

    @Column
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @Column
    private Boolean authoritative;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private Instant created;

    @Column
    private Instant updated;

    public Researcher(Set<Identity> identities, String organisation, String organisationUid, EmployeeType employeeType,
                      Boolean authoritative, String name, String email) {
        this.identities = identities;
        this.organisation = organisation;
        this.organisationUid = organisationUid;
        this.employeeType = employeeType;
        this.authoritative = authoritative;
        this.name = name;
        this.email = email;
    }
}

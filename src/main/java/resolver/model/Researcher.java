package resolver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "researchers")
@Getter
@NoArgsConstructor
public class Researcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "researchers_identities",
        joinColumns = @JoinColumn(name = "researcher_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "identity_id", referencedColumnName = "id"))
    private Set<Identity> identities = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "researchers_parents_children",
        joinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"))
    private Set<Researcher> parents = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "researchers_parents_children",
        joinColumns = @JoinColumn(name = "child_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id"))
    private Set<Researcher> children = new HashSet<>();

    @Column
    @NotNull
    private String organisation;

    @Column
    @NotNull
    private String organisationUid;

    @OneToMany(mappedBy = "researcher")
    private Set<Author> authors = new HashSet<>();

    @Column
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @Column
    private Boolean isAuthorative;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private Instant created;

    @Column
    private Instant updated;
}

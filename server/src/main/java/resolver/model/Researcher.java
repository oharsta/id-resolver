package resolver.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Setter
public class Researcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "researcher", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Identity> identities = new HashSet<>();

    @OneToMany(mappedBy = "child")
    private Set<ResearcherRelation> parents = new HashSet<>();

    @OneToMany(mappedBy = "parent")
    private Set<ResearcherRelation> children = new HashSet<>();

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
    private Boolean isAuthoritative;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private Instant created;

    @Column
    private Instant updated;

}

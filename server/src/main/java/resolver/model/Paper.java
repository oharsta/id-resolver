package resolver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "papers")
@Getter
@NoArgsConstructor
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "paper", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Authorship> authorships = new HashSet<>();

    @NotNull
    @Column
    private String title;

    @Column
    private String publisher;

    @Column
    @NotNull
    private Instant published;

    @Column
    private String doi;

    @Column
    private String publicUrl;
}

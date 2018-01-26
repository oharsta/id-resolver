package resolver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name = "researcher_parents")
@Table(name = "researcher_relations")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ResearcherParent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @NotNull
    private Researcher parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    @NotNull
    @JsonIgnore
    private Researcher child;

    @Column
    @NotNull
    private Integer weight;

    public ResearcherParent(Researcher parent, Researcher child, @NotNull Integer weight) {
        this.parent = parent;
        this.child = child;
        this.weight = weight;
    }

}

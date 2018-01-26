package resolver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity(name = "researcher_relations")
@Getter
@NoArgsConstructor
public class ResearcherRelation {

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
    private Researcher child;

    @Column
    @NotNull
    private Integer weight;

    public ResearcherRelation(Researcher parent, Researcher child, @NotNull Integer weight) {
        this.parent = parent;
        this.child = child;
        this.weight = weight;
    }

    public void setParent(Researcher parent) {
        this.parent = parent;
    }

    public void setChild(Researcher child) {
        this.child = child;
    }
}

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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class ResearcherView extends Researcher {

    public ResearcherView(Researcher researcher) {
        this.setName(researcher.getName());
        this.setEmail(researcher.getEmail());
        this.setId(researcher.getId());
        this.setAuthoritative(researcher.getAuthoritative());
        this.setIdentities(researcher.getIdentities());
        this.setOrganisation(researcher.getOrganisation());
        this.setOrganisationUid(researcher.getOrganisationUid());
    }

    @JsonIgnore
    @Override
    public Set<ResearcherRelation> getParents() {
        return super.getParents();
    }

    @JsonIgnore
    @Override
    public Set<ResearcherRelation> getChildren() {
        return super.getChildren();
    }

    @JsonIgnore
    @Override
    public Set<Author> getAuthors() {
        return super.getAuthors();
    }
}

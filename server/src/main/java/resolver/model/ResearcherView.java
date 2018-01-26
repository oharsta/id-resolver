package resolver.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ResearcherView extends ResearcherRelationView {

    private Set<ResearcherRelationView> children;
    private Set<ResearcherRelationView> parents;
    private Set<Authorship> authorships = new HashSet<>();

    public ResearcherView(Researcher researcher) {
        super(researcher, null);
        this.children = researcher.getChildren().stream().map(child -> new ResearcherRelationView(child.getChild(), child.getWeight()))
            .collect(Collectors.toSet());
        this.parents = researcher.getParents().stream().map(parent -> new ResearcherRelationView(parent.getParent(), parent.getWeight()))
            .collect(Collectors.toSet());
        this.authorships = researcher.getAuthorships();

    }
}

package resolver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity(name = "identities")
@Getter
@NoArgsConstructor
public class Identity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String identityValue;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private IdentityType identityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "researcher_id")
    @JsonIgnore
    private Researcher researcher;

    public Identity(String identityValue, IdentityType identityType) {
        this.identityValue = identityValue;
        this.identityType = identityType;
    }

    public static Set<Identity> identities(List<String> values, List<IdentityType> types) {
        Assert.isTrue(values.size() == types.size(), "Values and types must be of equal size");
        return IntStream.range(0, values.size()).mapToObj(i -> new Identity(values.get(i), types.get
            (i))).collect(Collectors.toSet());
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }
}

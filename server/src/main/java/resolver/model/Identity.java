package resolver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
import java.time.Instant;

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


}

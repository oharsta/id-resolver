package resolver.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import resolver.AbstractIntegrationTest;
import resolver.model.IdentityType;
import resolver.model.Researcher;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ResearcherRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private ResearcherRepository repository;

    @Test
    public void findByIdentitiesIdentityValueAndIdentitiesIdentityType() {
        List<Researcher> researchers = repository
            .findByIdentitiesIdentityValueAndIdentitiesIdentityType("https://orcid.org/0000-0002-3843-3472",
                IdentityType.ORCID);
        assertEquals(1, researchers.size());
    }

    @Test
    public void findByEmail() {
        List<Researcher> researchers = repository
            .findByEmailIgnoreCase("STEWARD.DOE@EXAMPLE.ORG");
        assertEquals(1, researchers.size());
    }

    @Test
    public void countByOrganisation() {
        long c = repository.countByOrganisationDistinct();
        assertEquals(1, c);
    }

    @Test
    public void countByIdentityValue() {
        long c = repository.countByIdentityValueDistinct();
        assertEquals(3, c);
    }

    @Test
    public void findByPapers() {
        Researcher researcher = new Researcher();
        researcher.setId(1L);
        List<Researcher> byPapers = repository.findByJoinedPapers(researcher);
        assertEquals(1, byPapers.size());
        assertEquals("mary.doe@example.org", byPapers.get(0).getEmail());
    }
}
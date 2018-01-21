package resolver.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import resolver.AbstractIntegrationTest;
import resolver.model.IdentityType;
import resolver.model.Researcher;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ResearcherRelationRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private ResearcherRelationRepository repository;

    @Test
    public void groupByWeight() {
        Object[][] o = repository.groupByWeight();
        Map<Object, Object> m = Stream.of(o).collect(Collectors.toMap(obj -> obj[0], obj -> obj[1]));
        this.assertGroupByValue(m, 10, 1);
        this.assertGroupByValue(m, 50, 1);
        this.assertGroupByValue(m, 100, 1);
    }

    private void assertGroupByValue(Map<Object, Object> m, Object weight, int expected) {
        assertEquals(expected, BigInteger.class.cast(m.get(weight)).intValue());
    }
}
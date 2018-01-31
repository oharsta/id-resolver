package resolver.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ResearcherTest {

    @Test
    public void testEquals() {
        Researcher r1 = new Researcher();
        r1.setId(1L);

        Researcher r2 = new Researcher();
        r2.setId(1L);

        assertEquals(r1, r2);
        Set<Researcher> researchers = new HashSet<>();
        researchers.add(r1);
        researchers.add(r2);

        assertEquals(1, researchers.size());
    }

}
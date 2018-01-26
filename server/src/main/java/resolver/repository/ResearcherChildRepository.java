package resolver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import resolver.model.Researcher;
import resolver.model.ResearcherChild;

@Repository
public interface ResearcherChildRepository extends CrudRepository<ResearcherChild, Long> {

    @Query(value = "SELECT weight, count(weight) FROM researcher_relations GROUP BY weight ORDER BY weight",
        nativeQuery = true)
    Object[][] groupByWeight();

    long deleteByParent(Researcher parent);

}

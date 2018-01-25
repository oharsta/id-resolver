package resolver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import resolver.model.ResearcherRelation;

@Repository
public interface ResearcherRelationRepository extends CrudRepository<ResearcherRelation, Long> {

    @Query(value = "SELECT weight, count(weight) FROM researcher_relations GROUP BY weight ORDER BY weight",
        nativeQuery = true)
    Object[][] groupByWeight();

}

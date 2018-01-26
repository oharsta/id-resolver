package resolver.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import resolver.model.Researcher;
import resolver.model.ResearcherParent;

@Repository
public interface ResearcherParentRepository extends CrudRepository<ResearcherParent, Long> {

    long deleteByChild(Researcher child);
}
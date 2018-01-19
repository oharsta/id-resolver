package resolver.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import resolver.model.Researcher;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResearcherRepository extends CrudRepository<Researcher, Long> {

    @EntityGraph(value = "findByOrganisationAndOrganisationUid", type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = {"parents", "children", "authors"})
    Optional<Researcher> findByOrganisationAndOrganisationUid(String organisation, String organisationUid);

    @EntityGraph(value = "findById", type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = {"parents.parent", "parents.child", "children.parent", "children.child", "authors"})
    Optional<Researcher> findById(Long id);

    @Query("select r from researchers r left outer join r.identities i " +
        "where lower(r.email) like %:term% or lower(r.name) like %:term% " +
        "or lower(i.identityValue) like %:term% " +
        "or lower(r.organisationUid) like %:term% " +
        "or lower(r.organisation) like %:term% ")
    List<Researcher> findByVarious(@Param("term") String term);
}

package resolver.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import resolver.model.IdentityType;
import resolver.model.Researcher;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResearcherRepository extends CrudRepository<Researcher, Long> {

    @EntityGraph(value = "findById", type = EntityGraph.EntityGraphType.LOAD,
        attributePaths = {"parents.parent", "parents.child", "children.parent", "children.child", "authorships",
            "identities"})
    Optional<Researcher> findById(Long id);

    @Query("select r from researchers r left outer join r.identities i where " +
        "lower(r.email) like %:term% " +
        "or lower(r.name) like %:term% " +
        "or lower(i.identityValue) like %:term% " +
        "or lower(r.organisationUid) like %:term% " +
        "or lower(r.organisation) like %:term% ")
    List<Researcher> findByVarious(@Param("term") String term);

    List<Researcher> findByIdentitiesIdentityValueAndIdentitiesIdentityType(String identityValue, IdentityType
        identityType);

    List<Researcher> findByEmailIgnoreCase(String email);

    @Override
    @EntityGraph(value = "findAll", type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"identities"})
    List<Researcher> findAll();

    @Query(value = "select count(distinct organisation) from researchers", nativeQuery = true)
    long countByOrganisationDistinct();

    @Query(value = "select count(distinct identity_value) from identities", nativeQuery = true)
    long countByIdentityValueDistinct();

    @Query("select r from researchers r inner join r.authorships a inner join a.paper p " +
        "where a.coAuthor is 1 and r is not :researcher and p.id in " +
        "(select p2.id from papers p2 inner join p2.authorships a2 " +
        "inner join a2.researcher r2 where r2 is :researcher and a2.coAuthor is 1)")
    List<Researcher> findByJoinedPapers(@Param("researcher") Researcher researcher);
}

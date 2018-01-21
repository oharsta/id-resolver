package resolver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import resolver.api.APIUser;
import resolver.exception.ResourceNotFoundException;
import resolver.model.Researcher;
import resolver.model.ResearcherRelation;
import resolver.model.ResearcherView;
import resolver.repository.ResearcherRelationRepository;
import resolver.repository.ResearcherRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@RestController()
@RequestMapping(path = "/resolver/api")
public class ResearcherController {

    @Autowired
    private ResearcherRepository researcherRepository;

    @Autowired
    private ResearcherRelationRepository researcherRelationRepository;

    @GetMapping("/researchers")
    public List<Researcher> all(APIUser apiUser) {
        return researcherRepository.findAll();
    }

    @GetMapping("/researchers/{id}")
    @Transactional(readOnly = true)
    public Researcher researcherById(APIUser apiUser, @PathVariable("id") Long id) {
        Researcher researcher = researcherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
            (String.format("Researcher with id %s not found", id)));
        return replacePersistentObjectWithViewObjects(researcher);
    }

    private Researcher replacePersistentObjectWithViewObjects(Researcher researcher) {
        researcher.setChildren(researcher.getChildren().stream().map(researcherRelation -> transform
            (researcherRelation, true)).collect(toSet()));
        researcher.setParents(researcher.getParents().stream().map(researcherRelation -> transform
            (researcherRelation, false)).collect(toSet()));
        return researcher;
    }

    private ResearcherRelation transform(ResearcherRelation researcherRelation, boolean isParent) {
        researcherRelation.setParent(isParent ? null : new ResearcherView(researcherRelation.getParent()));
        researcherRelation.setChild(isParent ? new ResearcherView(researcherRelation.getChild()) : null);
        return researcherRelation;
    }

    @GetMapping("/researchers/{organisation}/{organisationUid}")
    public Researcher researcherByOrgAndOrgUid(APIUser apiUser, @PathVariable("organisation") String organisation,
                                               @PathVariable("organisationUid") String organisationUid) {
        Researcher researcher = researcherRepository.findByOrganisationAndOrganisationUid(organisation,
            organisationUid).orElseThrow(
            () -> new ResourceNotFoundException(String.format("Researcher with organisation %s and organisationUid %s" +
                " not found", organisation, organisationUid)));
        return replacePersistentObjectWithViewObjects(researcher);
    }

    @GetMapping("find/researchers")
    public List<Researcher> find(@RequestParam("q") String q) {
        return researcherRepository.findByVarious(q.toLowerCase());
    }

    @PostMapping("/researchers")
    public List<Researcher> newResearcher(@Validated @RequestBody Researcher researcher) {
        //TODO matching based on papers, id's and emails
        return null;
    }

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        Map<String, Object> result = new HashMap<>();
        result.put("organisations",researcherRepository.countByOrganisationDistinct());
        result.put("researchers",researcherRepository.count());
        result.put("identities", researcherRepository.countByIdentityValueDistinct());
        result.put("weights", Stream.of(researcherRelationRepository.groupByWeight()).collect(Collectors
            .toMap(obj -> obj[0], obj -> obj[1])));
        return result;
    }
}

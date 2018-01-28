package resolver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import resolver.api.APIUser;
import resolver.exception.NotAllowedException;
import resolver.exception.ResourceNotFoundException;
import resolver.model.Identity;
import resolver.model.Researcher;
import resolver.model.ResearcherChild;
import resolver.model.ResearcherParent;
import resolver.model.ResearcherView;
import resolver.repository.ResearcherChildRepository;
import resolver.repository.ResearcherParentRepository;
import resolver.repository.ResearcherRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestController()
@RequestMapping(path = {"/api/resolver", "/client"})
public class ResearcherController {

    @Autowired
    private ResearcherRepository researcherRepository;

    @Autowired
    private ResearcherParentRepository researcherParentRepository;

    @Autowired
    private ResearcherChildRepository researcherChildRepository;

    @GetMapping("/researchers")
    public List<Researcher> all(APIUser apiUser) {
        return researcherRepository.findAll();
    }

    @GetMapping("/researchers/{id}")
    @Transactional(readOnly = true)
    public ResearcherView researcherById(APIUser apiUser, @PathVariable("id") Long id) {
        return new ResearcherView(researcherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
            (String.format("Researcher with id %s not found", id))));
    }

    @DeleteMapping("/researchers/{id}")
    public void deleteResearcherById(APIUser apiUser, @PathVariable("id") Long id) {
        ResearcherView researcherView = this.researcherById(apiUser, id);
        if (!apiUser.getOrganisation().equals(researcherView.getOrganisation())) {
            throw new NotAllowedException(String.format("Not allowd to Delete Research with organisation %s for " +
                "user with organisation %s.", researcherView.getOrganisation(), apiUser.getOrganisation()));
        }
        researcherRepository.deleteById(id);
    }

    @GetMapping("/researchers/{organisation}/{organisationUid}")
    public ResearcherView researcherByOrgAndOrgUid(APIUser apiUser, @PathVariable("organisation") String organisation,
                                                   @PathVariable("organisationUid") String organisationUid) {
        return new ResearcherView(researcherRepository.findByOrganisationAndOrganisationUid(organisation,
            organisationUid).orElseThrow(
            () -> new ResourceNotFoundException(String.format("Researcher with organisation %s and organisationUid %s" +
                " not found", organisation, organisationUid))));
    }

    @GetMapping("find/researchers")
    public Set<Researcher> find(@RequestParam("q") String q) {
        return new HashSet<>(researcherRepository.findByVarious(q.toLowerCase()));
    }

    @PostMapping("/researchers")
    @Transactional
    public ResearcherView newResearcher(APIUser apiUser, @Validated @RequestBody Researcher researcher) {
        if (!apiUser.getOrganisation().equals(researcher.getOrganisation())) {
            throw new NotAllowedException(String.format("Not allowd to POST / PUT Research with organisation %s for " +
                "user with organisation %s.", researcher.getOrganisation(), apiUser.getOrganisation()));
        }
        researcher.getIdentities().forEach(identity -> identity.setResearcher(researcher));
        final Researcher saved = researcherRepository.save(researcher);

        Set<Identity> identities = saved.getIdentities();
        List<Researcher> relationsByIdentity = identities.stream().map(identity -> researcherRepository
            .findByIdentitiesIdentityValueAndIdentitiesIdentityType(identity.getIdentityValue(), identity
                .getIdentityType()))
            .flatMap(List::stream)
            .filter(rel -> !rel.getId().equals(saved.getId()))
            .collect(toList());

        List<Researcher> relationsByEmailUnfiltered = StringUtils.hasText(saved.getEmail()) ?
            researcherRepository.findByEmailIgnoreCase(saved.getEmail()) : new ArrayList<>();
        List<Researcher> relationsByEmails = relationsByEmailUnfiltered.stream().
            filter(rel -> relationsByIdentity.stream()
                .noneMatch(r -> r.getId().equals(rel.getId())) && !rel.getId().equals(saved.getId())).collect(toList());

        List<Researcher> relationsByPapersUnfiltered = researcherRepository.findByJoinedPapers(saved);
        List<Researcher> relationsByPapers = relationsByPapersUnfiltered.stream()
            .filter(rel -> relationsByIdentity.stream().noneMatch(r -> r.getId().equals(rel.getId())) &&
                relationsByEmails.stream().noneMatch(r2 -> r2.getId().equals(rel.getId()))
                && !rel.getId().equals(saved.getId()))
            .collect(toList());


        if (saved.getAuthoritative()) {
            List<ResearcherChild> children = relationsByIdentity.stream().map(res -> new ResearcherChild(saved, res,
                100)).collect(toList());
            children.addAll(relationsByEmails.stream().map(res -> new ResearcherChild(saved, res,
                50)).collect(toList()));
            children.addAll(relationsByPapers.stream().map(res -> new ResearcherChild(saved, res,
                10)).collect(toList()));

            saved.getChildren().addAll(children);
            children.forEach(child -> researcherChildRepository.save(child));

            //If both are authoritative then we link them both ways
            relationsByIdentity.stream().filter(rel -> rel.getAuthoritative()).forEach(res ->
                researcherParentRepository.save
                    (new ResearcherParent(res, saved, 100)));
            relationsByEmails.stream().filter(rel -> rel.getAuthoritative()).forEach(res ->
                researcherParentRepository.save
                    (new ResearcherParent(res, saved, 50)));
            relationsByPapers.stream().filter(rel -> rel.getAuthoritative()).forEach(res ->
                researcherParentRepository.save
                    (new ResearcherParent(res, saved, 10)));
        } else {
            List<ResearcherParent> parents = relationsByIdentity.stream().map(res -> new ResearcherParent(res, saved,
                100)).collect(toList());
            parents.addAll(relationsByEmails.stream().map(res -> new ResearcherParent(res, saved,
                50)).collect(toList()));
            parents.addAll(relationsByPapers.stream().map(res -> new ResearcherParent(res, saved,
                10)).collect(toList()));
            saved.getParents().addAll(parents);
            parents.forEach(parent -> researcherParentRepository.save(parent));
        }

        return new ResearcherView(saved);
    }

    @PutMapping("/researchers")
    @Transactional
    public ResearcherView updateResearcher(APIUser apiUser, @Validated @RequestBody Researcher researcher) {
        //delete all existing relations and re-create them
        this.researcherChildRepository.deleteByParent(researcher);
        this.researcherParentRepository.deleteByChild(researcher);
        return this.newResearcher(apiUser, researcher);
    }


    @GetMapping("/stats")
    public Map<String, Object> stats() {
        Map<String, Object> result = new HashMap<>();
        result.put("organisations", researcherRepository.countByOrganisationDistinct());
        result.put("researchers", researcherRepository.count());
        result.put("identities", researcherRepository.countByIdentityValueDistinct());
        result.put("relations", researcherChildRepository.count());
        result.put("weights", Stream.of(researcherChildRepository.groupByWeight()).collect(Collectors
            .toMap(obj -> obj[0], obj -> obj[1])));
        return result;
    }
}

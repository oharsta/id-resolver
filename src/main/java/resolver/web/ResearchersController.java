package resolver.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import resolver.api.APIUser;
import resolver.exception.ResourceNotFoundException;
import resolver.model.Researcher;
import resolver.repository.ResearcherRepository;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
public class ResearchersController {

    @Autowired
    private ResearcherRepository researcherRepository;

    @GetMapping("/researchers")
    public List<Researcher> all(APIUser apiUser) {
        List<Researcher> researchers = StreamSupport.stream(researcherRepository.findAll().spliterator(), false).collect
            (toList());
        return researchers;
    }

    @GetMapping("/researchers/{id}")
    public Researcher researcherById(APIUser apiUser, @PathVariable("id") Long id) {
        return researcherRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format
            ("Researcher with id %s not found", id)));
    }

    @GetMapping("/researchers/{organisation}/{organisationUid}")
    public Researcher researcherByOrgAndOrgUid(APIUser apiUser, @PathVariable("organisation") String organisation,
                                               @PathVariable("organisationUid") String organisationUid) {
        return researcherRepository.findByOrganisationAndOrganisationUid(organisation, organisationUid).orElseThrow(
            () -> new ResourceNotFoundException(String.format("Researcher with organisation %s and organisationUid %s" +
                " not found", organisation, organisationUid)));
    }

    @GetMapping("find/researchers")
    public List<Researcher> find(@RequestParam("q") String q) {
        return researcherRepository.findByVarious(q);
    }

    @PostMapping("/researchers")
    public List<Researcher> newResearcher(@Validated @RequestBody Researcher researcher) {
        //TODO matching based on papers, id's and emails
        return null;
    }
}

package fr.dauphine.miageIf.Site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sites")
public class SiteController {
    @Autowired
    private SiteRepository siteRepository;

    //récupérer/lister,
    @GetMapping("/all")
    public ResponseEntity<List<Site>> getAllSites() {
        List<Site> sites = siteRepository.findAll();
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/id/{id}")
    public Optional<Site> getSiteById(@PathVariable Long id) {
        return siteRepository.findById(id);
    }


    //http://localhost:8000/sites/name/Stade%20Olympique
    //les espaces sont pas geres par lapplication
    @GetMapping("/name/{nomSite}")
    public Site findByName(@PathVariable String nomSite){
        return siteRepository.findSiteByNomSite(nomSite);
    }


    // Créer un nouveau site olympique
    @PostMapping
    public Site createSite(@RequestBody Site site) {
        return siteRepository.save(site);
    }

    // MODIFIER un site existant
    @PutMapping("/id/{id}")
    public ResponseEntity<Site> updateSite(@PathVariable Long id, @RequestBody Site siteDetails) {
        Optional<Site> optionalSite = siteRepository.findById(id);
        if (optionalSite.isPresent()) {
            Site site = optionalSite.get();
            site.setNomSite(siteDetails.getNomSite());
            site.setInfoGeographique(siteDetails.getInfoGeographique());
            site.setSiteParalympique(siteDetails.getSiteParalympique());
            Site updatedSite = siteRepository.save(site);
            return ResponseEntity.ok(updatedSite);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un site by id
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        Optional<Site> optionalSite = siteRepository.findById(id);
        if (optionalSite.isPresent()) {
            siteRepository.delete(optionalSite.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

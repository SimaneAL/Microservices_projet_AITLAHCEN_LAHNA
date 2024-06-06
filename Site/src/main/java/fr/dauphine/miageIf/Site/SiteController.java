package fr.dauphine.miageIf.Site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/site")
public class SiteController {
    @Autowired
    private SiteRepository siteRepository;

    //récupérer/lister tous les sites
    @GetMapping("/all")
    public ResponseEntity<List<Site>> getAllSites() {
        List<Site> sites = siteRepository.findAll();
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/id/{id}")
    public Optional<Site> getSiteById(@PathVariable Long id) {
        return siteRepository.findById(id);
    }

    // si y a des espaces dans le nom du site
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


    // MODIFIER un site existant avec id
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
    //et donc appel au microservices pour egalement supprimer les events lies a ce site supprime
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        Optional<Site> optionalSite = siteRepository.findById(id);
        if (optionalSite.isPresent()) {
            siteRepository.delete(optionalSite.get());
            RestTemplate restTemplate = new RestTemplate();
            //ici on fait appel au microservice calendrier pour supprimer les events lies a ce site supprime
            String url = "http://localhost:8002/calendrier/deleteByIdSite/" + id;
            System.out.println("Request URL: " + url);
            try {
                HttpEntity<Void> requestEntity = new HttpEntity<>(null);
                restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

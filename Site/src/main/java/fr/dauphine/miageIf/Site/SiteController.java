package fr.dauphine.miageIf.Site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/site")
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
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8002/calendrier/deleteByIdSite/" + id;
            System.out.println("Request URL: " + url);

            try {
                // Create an HttpEntity with no body
                HttpEntity<Void> requestEntity = new HttpEntity<>(null);

                // Send the DELETE request
                ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);

                // Check response status code
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Successfully deleted the calendar entry for site ID: " + id);
                } else {
                    System.out.println("Failed to delete the calendar entry, status code: " + response.getStatusCode());
                }

            } catch (Exception e) {
                System.out.println("Error during DELETE request to the calendrier microservice: " + e.getMessage());
                e.printStackTrace();
            }
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

package fr.dauphine.miageIf.Calendrier;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/calendrier")
public class CalendrierController {
    @Autowired
    private CalendrierRepository calendrierRepository;

    @Autowired
    private RestTemplate restTemplate;

    //récupérer/lister,
    @GetMapping("/all")
    public ResponseEntity<List<Calendrier>> getAllSCalendriers() {
        List<Calendrier> ca = calendrierRepository.findAll();
        return ResponseEntity.ok(ca);
    }

    @GetMapping("/id/{id}")
    public Optional<Calendrier> getCalendrierById(@PathVariable Long id) {
        return calendrierRepository.findById(id);
    }

    //gérer le calendrier olympique des sports (modifier, supprimer)

    // Créer un nouveau calendrier olympique
    @PostMapping
    public Calendrier creerCalendrier(@RequestBody Calendrier site) {
        return calendrierRepository.save(site);
    }

    // Modifier un calendrier existant
    @PutMapping("/id/{id}")
    public ResponseEntity<Calendrier> updateCalendrier(@PathVariable Long id, @RequestBody Calendrier calendrierDetails) {
        Optional<Calendrier> optionalCalendrier = calendrierRepository.findById(id);
        if (optionalCalendrier.isPresent()) {
            Calendrier calendrier = optionalCalendrier.get();
            calendrier.setIdSite(calendrierDetails.getIdSite());
            calendrier.setIdSport(calendrierDetails.getIdSport());
            calendrier.setDate(calendrierDetails.getDate());
            Calendrier updatedCalendrier = calendrierRepository.save(calendrier);
            return ResponseEntity.ok(updatedCalendrier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un calendrier by id
    //donc suppression de ce dernier dans le microservices planning
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteCalendrier(@PathVariable Long id) {
        Optional<Calendrier> optionalCalendrier = calendrierRepository.findById(id);
        if (optionalCalendrier.isPresent()) {
            calendrierRepository.delete(optionalCalendrier.get());
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8003/planning/deleteByIdCalendrier/" + id;

                try {
                    // Create an HttpEntity with no body
                    HttpEntity<Void> requestEntity = new HttpEntity<>(null);

                    // Send the DELETE request
                    ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
                return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Supprimer un calendrier by idSite
    @Transactional
    @DeleteMapping("/deleteByIdSite/{siteId}")
    public ResponseEntity<Void> deletePlanningBySiteId(@PathVariable Long siteId) {
        List<Calendrier> plannings = calendrierRepository.findByIdSite(siteId);
        if (plannings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        calendrierRepository.deleteByIdSite(siteId);
        return ResponseEntity.noContent().build();
    }

    // Supprimer un calendrier by isSport
    @Transactional
    @DeleteMapping("/deleteByIdSport/{sportId}")
    public ResponseEntity<Void> deletePlanningBySportId(@PathVariable Long sportId) {
        List<Calendrier> plannings = calendrierRepository.findByIdSite(sportId);
        if (plannings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        calendrierRepository.deleteByIdSport(sportId);
        return ResponseEntity.noContent().build();
    }


    // cette methode converti le type String en type Date
    public static Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.parse(dateString);
    }


    //renvoyer les calendrier a une date donnee
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Calendrier>> getCalendrierByDate(@PathVariable String date) throws ParseException {
        return ResponseEntity.ok(calendrierRepository.findCalendrierByDate(convertStringToDate(date)));
    }

    //nom des sports a une date donnee
    //on fait donc appel au microservices sport
    @GetMapping("/nomSportByDate/{date}")

    public ResponseEntity<List<String>> getSportByDate(@PathVariable String date) throws ParseException {

        try {
            //ici list des calendrier a une date donnee
            List<Calendrier> evenements = calendrierRepository.findCalendrierByDate(convertStringToDate(date));

            //ici je veux les id des sports dans la liste preced
            List<Long> sportIds = new ArrayList<>();
            for (Calendrier evenement : evenements) {
                sportIds.add(evenement.getIdSport());
            }

            // here je fais l appel à la methode qui appele le microservice sport
            List<String> nomsSports = new ArrayList<>();
            for (Long sportId : sportIds) {
                String nomSport = getSportsbyId(sportId);
                nomsSports.add(nomSport);
            }
            return ResponseEntity.ok(nomsSports);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //nom des sites a une date donnee
    //on fait donc appel au microservices site
    @GetMapping("/nomSiteByDate/{date}")

    public ResponseEntity<List<String>> getSiteByDate(@PathVariable String date) throws ParseException {

        try {
            //ici list des calendrier a une date donnee
            List<Calendrier> evenements = calendrierRepository.findCalendrierByDate(convertStringToDate(date));

            //ici je veux les id des sites dans la liste preced
            List<Long> sitesIds = new ArrayList<>();
            for (Calendrier evenement : evenements) {
                sitesIds.add(evenement.getIdSite());
            }

            // here je fais l appel à la methode qui appele le microservice site
            List<String> nomsSites = new ArrayList<>();
            for (Long siteId : sitesIds) {
                String nomSite = getSiteNameBySiteId(siteId);
                nomsSites.add(nomSite);
            }
            return ResponseEntity.ok(nomsSites);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //renvoyer le/les site(s) en fonction de son id en path
    //on fait donc appel au microservice site pour reuperer le nom
    @GetMapping("/nameSite/id/{id}")
    public String getSiteNameBySiteId(@PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/site/id/" + id;

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("nomSite")) {
                return response.get("nomSite").toString();
            } else {
                return "Site introuvable";
            }
        } catch (Exception e) {
            return "Lancez le microservice Site !";
        }
    }


    //renvoyer le/les sport en fonction de son id en path
    //on fait donc appel au microservice sport pour reuperer le nom
    @GetMapping("/nameSport/id/{id}")
    public String getSportsbyId(@PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8001/sport/id/" + id;

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("nomSport")) {
                return response.get("nomSport").toString();
            } else {
                return "Sport introuvable";
            }
        } catch (Exception e) {
            return "Lancez le microservice Sport !";
        }
    }







    @GetMapping("/getSportsDansUnSiteDonne/{nomSite}")
    public List<String> getSportsDansUnSiteDonne(@PathVariable String nomSite) {
        String url = "http://localhost:8000/site/name/" + nomSite;
        RestTemplate restTemplate = new RestTemplate();
        List<String> sports = new ArrayList<>();
//        List<Map<String, Object>> listNom = restTemplate.getForObject(url, List.class);
        List<Calendrier> listIdsSports;
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("id")) {
                Long idSite = Long.valueOf(response.get("id").toString());
                //je recup les sports dans ce site
                listIdsSports = calendrierRepository.findByIdSport(idSite);
            } else {
                System.out.println("Site introuvable");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Lancez le microservice Site !");
            return null;
        }

        try {
            for (Calendrier c : listIdsSports) {
                String urlSports = "http://localhost:8001/sport/id/" + c.getIdSport();
                Map<String, Object> sportsNoms = restTemplate.getForObject(urlSports, Map.class);
                if (sportsNoms != null && sportsNoms.containsKey("nomSport")) {
                    sports.add(sportsNoms.get("nomSport").toString());
                } else {
                    System.out.println("sport introuvable");
                    return null;
                }
            }

        } catch (Exception e) {
            System.out.println("Lancez le microservice sport !");
            return null;
        }

        System.out.println(sports);
        // List<Map<String, Object>> listSports = restTemplate.getForObject(url, List.class);
        return sports;

    }


    @GetMapping("/siteAvecSportDonne/{nomSport}")
    public List<String> getSitesNameAvecSportDonne(@PathVariable String nomSport) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8001/sport/name/" + nomSport;
        List<String> sites = new ArrayList<>();
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("id")) {
                Long idSportRecupere = Long.valueOf(response.get("id").toString());
                System.out.println("idRecu : " + idSportRecupere);
                List<Calendrier> list = calendrierRepository.findAllByIdSport(idSportRecupere);
                for (Calendrier c : list) {
                    System.out.println(c.getIdSport());
                    sites.add(this.getSiteNameBySiteId(c.getIdSport()));
                }
                System.out.println(sites);
                return sites;
            } else {
                System.out.println("Site introuvable");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Lancez le microservice Site !");
            return null;
        }
    }

}



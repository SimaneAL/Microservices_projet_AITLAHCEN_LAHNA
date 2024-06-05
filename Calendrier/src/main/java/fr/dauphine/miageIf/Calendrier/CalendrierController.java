package fr.dauphine.miageIf.Calendrier;

import org.springframework.beans.factory.annotation.Autowired;
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
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        Optional<Calendrier> optionalCalendrier = calendrierRepository.findById(id);
        if (optionalCalendrier.isPresent()) {
            calendrierRepository.delete(optionalCalendrier.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public static Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.parse(dateString);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Calendrier>> getCalendrierByDate(@PathVariable String date) throws ParseException {

        return ResponseEntity.ok(calendrierRepository.findCalendrierByDate(convertStringToDate(date)));
    }

    @GetMapping("/nameSite/id/{id}")
    public String getSiteName(@PathVariable Long id) {
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

    @GetMapping("/nameSport/id/{id}")
    public String getSitesNameWhereSport(@PathVariable Long id) {
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


    @GetMapping("/sportDansSiteDonne/{nomSport}")
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
//                System.out.println(list.get(1).toString());
//                System.out.println(list.get(2).toString());
                for (Calendrier c : list) {
                    System.out.println(c.getIdSport());
                    sites.add(this.getSiteName(c.getIdSport()));
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



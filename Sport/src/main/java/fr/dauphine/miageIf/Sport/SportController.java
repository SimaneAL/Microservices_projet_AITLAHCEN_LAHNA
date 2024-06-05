package fr.dauphine.miageIf.Sport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sport")
public class SportController {
    @Autowired
    private SportRepository sportRepository;

    // Récupérer/lister tous les sports
    @GetMapping("/all")
    public ResponseEntity<List<Sport>> getAllSports() {
        List<Sport> sports = sportRepository.findAll();
        return ResponseEntity.ok(sports);
    }

    // Récupérer un sport par son ID
    @GetMapping("/id/{id}")
    public Optional<Sport> getSportById(@PathVariable Long id) {
        return sportRepository.findById(id);
    }

    // Récupérer un sport par son nom
    @GetMapping("/name/{nomSport}")
    public Sport findByName(@PathVariable String nomSport) {
        return sportRepository.findSportByNomSport(nomSport);
    }

    // Créer un nouveau sport
    @PostMapping
    public Sport createSport(@RequestBody Sport sport) {
        return sportRepository.save(sport);
    }

    // Modifier un sport existant by id
    @PutMapping("/id/{id}")
    public ResponseEntity<Sport> updateSportById(@PathVariable Long id, @RequestBody Sport sportDetails) {
        Optional<Sport> optionalSport = sportRepository.findById(id);
        if (optionalSport.isPresent()) {
            Sport sport = optionalSport.get();
            sport.setNomSport(sportDetails.getNomSport());
            Sport updatedSport = sportRepository.save(sport);
            return ResponseEntity.ok(updatedSport);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un sport par son ID
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long id) {
        Optional<Sport> optionalSport = sportRepository.findById(id);
        if (optionalSport.isPresent()) {
            sportRepository.delete(optionalSport.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

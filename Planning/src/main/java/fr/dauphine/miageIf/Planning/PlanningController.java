package fr.dauphine.miageIf.Planning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/planning")
public class PlanningController {
    @Autowired
    private PlanningRepository planningRepository;

    //récupérer/lister,
    @GetMapping("/all")
    public ResponseEntity<List<Planning>> getAllPlannings() {
        List<Planning> ca = planningRepository.findAll();
        return ResponseEntity.ok(ca);
    }

    @GetMapping("/id/{id}")
    public Optional<Planning> getPlanningById(@PathVariable Long id) {
        return planningRepository.findById(id);
    }

    //gérer le calendrier olympique des sports (modifier, supprimer)

    // Créer un nouveau calendrier olympique
    @PostMapping
    public Planning createPlanning(@RequestBody Planning site) {
        return planningRepository.save(site);
    }

    // Modifier un planning existant
    @PutMapping("/id/{id}")
    public ResponseEntity<Planning> updatePlanning(@PathVariable Long id, @RequestBody Planning planningDetails) {
        Optional<Planning> optionalPlanning = planningRepository.findById(id);
        if (optionalPlanning.isPresent()) {
            Planning planning = optionalPlanning.get();
            planning.setPrenomSpectateur(planningDetails.getPrenomSpectateur());
            planning.setNomSpectateur(planningDetails.getNomSpectateur());
            planning.setIdCalendrier(planningDetails.getIdCalendrier());
            Planning updatedPlanning = planningRepository.save(planning);
            return ResponseEntity.ok(updatedPlanning);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un calendrier by id
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        Optional<Planning> optionalCalendrier = planningRepository.findById(id);
        if (optionalCalendrier.isPresent()) {
            planningRepository.delete(optionalCalendrier.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

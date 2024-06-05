package fr.dauphine.miageIf.Planning;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning, Long> {

    public List<Planning> getPlanningsByNomSpectateurAndAndPrenomSpectateur(String nomSpectateur, String prenomSpectateur);
   // public List<Planning> get
}

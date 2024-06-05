package fr.dauphine.miageIf.Planning;

import org.springframework.data.jpa.repository.JpaRepository;


// Creation du JPA Repository basé sur Spring Data
public interface PlanningRepository extends JpaRepository<Planning, Long> {

}

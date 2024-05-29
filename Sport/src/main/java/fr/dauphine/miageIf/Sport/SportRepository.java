package fr.dauphine.miageIf.Sport;

import org.springframework.data.jpa.repository.JpaRepository;


// Creation du JPA Repository basé sur Spring Data
public interface SportRepository extends JpaRepository<Sport, Long> {
    Sport findSportByNomSport(String nomSport);
}

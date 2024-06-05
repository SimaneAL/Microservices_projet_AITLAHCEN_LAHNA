package fr.dauphine.miageIf.Sport;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SportRepository extends JpaRepository<Sport, Long> {
    Sport findSportByNomSport(String nomSport);
}

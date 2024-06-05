package fr.dauphine.miageIf.Calendrier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;


// Creation du JPA Repository basé sur Spring Data
public interface CalendrierRepository extends JpaRepository<Calendrier, Long> {
    List<Calendrier> findCalendrierByDate(Date date);

    List<Calendrier> findCalendrierByDateAndIdSite(Date date, Long idSite);

    List<Calendrier> findCalendrierByDateAfter(Date date);

    List<Calendrier> findCalendrierByDateBefore(Date date);

    List<Calendrier> findCalendrierByDateBetween(Date dateStart, Date dateEnd);

    List<Calendrier> findByIdSport(Long idSport);

    List<Calendrier> findAllByIdSport(Long idSport);

    List<Calendrier> findAllByIdSite(Long idSite);

}

package fr.dauphine.miageIf.Calendrier;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface CalendrierRepository extends JpaRepository<Calendrier, Long> {
    List<Calendrier> findCalendrierByDate(Date date);

    List<Calendrier> findCalendrierByDateAfter(Date date);

    List<Calendrier> findCalendrierByDateBefore(Date date);

    List<Calendrier> findCalendrierByDateBetween(Date dateStart, Date dateEnd);
    List<Calendrier> findByIdSite(Long idSite);

    List<Calendrier> findByIdSport(Long idSport);

    List<Calendrier> findAllByIdSport(Long idSport);


    void deleteByIdSite(Long siteId);
    void deleteByIdSport(Long idSport);

}

package fr.dauphine.miageIf.Site;

import org.springframework.data.jpa.repository.JpaRepository;


// Creation du JPA Repository basé sur Spring Data
public interface SiteRepository extends JpaRepository<Site, Long> {
    Site findSiteByNomSite(String nomSite);


}

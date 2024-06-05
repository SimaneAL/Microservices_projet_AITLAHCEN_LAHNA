package fr.dauphine.miageIf.Site;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SiteRepository extends JpaRepository<Site, Long> {
    Site findSiteByNomSite(String nomSite);


}

package fr.dauphine.miageIf.Site;

import jakarta.persistence.*;
@Entity
@Table(name = "Site")
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_site")
    private String nomSite;

    @Column(name = "info_geographique")
    private String infoGeographique;

    @Column(name = "site_paralympique")
    private Boolean siteParalympique;

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSite() {
        return nomSite;
    }

    public void setNomSite(String nomSite) {
        this.nomSite = nomSite;
    }

    public String getInfoGeographique() {
        return infoGeographique;
    }

    public void setInfoGeographique(String infoGeographique) {
        this.infoGeographique = infoGeographique;
    }

    public Boolean getSiteParalympique() {
        return siteParalympique;
    }

    public void setSiteParalympique(Boolean siteParalympique) {
        this.siteParalympique = siteParalympique;
    }
}

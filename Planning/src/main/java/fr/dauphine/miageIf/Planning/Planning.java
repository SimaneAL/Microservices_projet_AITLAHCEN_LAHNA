package fr.dauphine.miageIf.Planning;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "planning")
public class Planning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_spectateur")
    private String nomSpectateur;

    @Column(name = "prenom_spectateur")
    private String prenomSpectateur;

    @Column(name = "nom_sport")
    private String nomSport;

    @Column(name = "date_sport")
    private Date dateSport;

    @Column(name = "nom_site")
    private String nomSite;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getNomSport() {
        return nomSport;
    }

    public void setNomSport(String nomSport) {
        this.nomSport = nomSport;
    }

    public Date getDateSport() {
        return dateSport;
    }


    public String getNomSpectateur() {
        return nomSpectateur;
    }

    public void setNomSpectateur(String nomSpectateur) {
        this.nomSpectateur = nomSpectateur;
    }

    public String getPrenomSpectateur() {
        return prenomSpectateur;
    }

    public void setPrenomSpectateur(String prenomSpectateur) {
        this.prenomSpectateur = prenomSpectateur;
    }

    public String getNomSite() {
        return nomSite;
    }

    public void setNomSite(String nomSite) {
        this.nomSite = nomSite;
    }

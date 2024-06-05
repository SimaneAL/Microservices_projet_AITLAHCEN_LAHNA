package fr.dauphine.miageIf.Planning;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "planning")
public class Planning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prenom_spectateur")
    private String prenomSpectateur;

    @Column(name = "nom_spectateur")
    private String nomSpectateur;


    @Column(name = "id_calendrier")
    private Long idCalendrier;



    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getIdCalendrier() {
        return idCalendrier;
    }

    public void setIdCalendrier(Long idCalendrier) {
        this.idCalendrier = idCalendrier;
    }
}

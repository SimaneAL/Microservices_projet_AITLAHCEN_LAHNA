package fr.dauphine.miageIf.Sport;

import jakarta.persistence.*;

// Classe persistente representant un "sport"
@Entity
@Table(name = "Sport")
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_sport")
    private String nomSport;

    // Getters et setters

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
}

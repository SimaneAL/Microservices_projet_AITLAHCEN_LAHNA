package fr.dauphine.miageIf.Calendrier;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "calendrier")
public class Calendrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_site")
    private Long idSite;

    @Column(name = "id_sport")
    private Long idSport;

    @Column(name = "date")
    private Date date;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSite() {
        return idSite;
    }

    public void setIdSite(Long idSite) {
        this.idSite = idSite;
    }

    public Long getIdSport() {
        return idSport;
    }

    public void setIdSport(Long idSport) {
        this.idSport = idSport;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

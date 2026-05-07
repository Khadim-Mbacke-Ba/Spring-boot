package sn.ucad.restou.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Etudiant etudiant;

    @Column(name = "code_ticket", nullable = false, unique = true)
    private String codeTicket;

    @Column(name = "date_achat", nullable = false)
    private LocalDateTime dateAchat;

    @Column(name = "date_validite", nullable = false)
    private LocalDate dateValidite;

    @Column(nullable = false)
    private Double prix;

    @Column(nullable = false)
    private Boolean utilise = false;

    // Constructeur vide
    public Ticket() {
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public String getCodeTicket() {
        return codeTicket;
    }

    public void setCodeTicket(String codeTicket) {
        this.codeTicket = codeTicket;
    }

    public LocalDateTime getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDateTime dateAchat) {
        this.dateAchat = dateAchat;
    }

    public LocalDate getDateValidite() {
        return dateValidite;
    }

    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Boolean getUtilise() {
        return utilise;
    }

    public void setUtilise(Boolean utilise) {
        this.utilise = utilise;
    }
}
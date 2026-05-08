package sn.ucad.restou.entity;

import jakarta.validation.constraints.*;
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
    // Validation pour s'assurer qu'un ticket est toujours associé à un étudiant
    @NotNull(message = "L'etudiant estobligatoire")
    @JsonIgnoreProperties({ " hibernateLazyInitializer ", "handler" })
    private Etudiant etudiant;
    // Validation pour s'assurer que le code du ticket est unique et respecte un
    // format spécifique
    @NotBlank(message = "Le code du ticket est obligatoire")
    @Pattern(regexp = "^TKT -\\d{4} -\\d{3}$", message = "Le code doit etre au format TKT -YYYY -NNN (ex: TKT-2025 -001)")
    @Column(name = "code_ticket", nullable = false, unique = true)
    private String codeTicket;
    // Validation pour s'assurer que la date d'achat est présente et ne peut pas
    // être dans le futur
    @NotNull(message = "La date d'achat estobligatoire")
    @PastOrPresent(message = "La date d'achat ne peut pas etre dans le futur")
    @Column(name = "date_achat", nullable = false)
    private LocalDateTime dateAchat;
    // Validation pour s'assurer que la date de validité est présente et doit être
    // aujourd'hui ou dans le futur
    @NotNull(message = "La date de validite est obligatoire")
    @FutureOrPresent(message = "La date de validite doit etre aujourd 'hui ou dans le futur")
    @Column(name = "date_validite", nullable = false)
    private LocalDate dateValidite;
    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit etre positif")
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
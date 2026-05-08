package sn.ucad.restou.entity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "etudiants")
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caracteres")
    @Column(name = "nom", nullable = false)
    private String nom;
    @NotBlank(message = "Le prenom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prenom doit contenir entre 2 et 50 caracteres")
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email n'est pas valide")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @ValidNumeroCarte
    @Column(name = "numero_carte", nullable = false, unique = true)
    private String numeroCarte;

    // Constructeur vide
    public Etudiant() {
    }

    // Constructeur complet
    public Etudiant(String nom, String prenom, String email, String numeroCarte) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numeroCarte = numeroCarte;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }
}
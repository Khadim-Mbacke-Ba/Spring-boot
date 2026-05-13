package sn.ucad.restou.dto;

public class AuthResponse {

    private String token;
    private String email;
    private String nom;
    private String prenom;
    private String role;

    public AuthResponse(String token, String email, String nom,
            String prenom, String role) {
        this.token = token;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    // Getters uniquement (immutable)

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getRole() {
        return role;
    }
}
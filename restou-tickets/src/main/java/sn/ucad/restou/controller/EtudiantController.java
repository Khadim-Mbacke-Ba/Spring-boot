package sn.ucad.restou.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sn.ucad.restou.entity.Etudiant;
import sn.ucad.restou.exception.ResourceNotFoundException;
import sn.ucad.restou.service.EtudiantService;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    // Récupérer tous les étudiants
    @GetMapping
    @PreAuthorize("hasAnyRole('GERANT','ADMIN')")
    public Iterable<Etudiant> recupererTous() {
        return etudiantService.recupererTous();
    }

    // Récupérer un étudiant par ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Etudiant> recupererParId(@PathVariable Long id) {

        Etudiant etudiant = etudiantService.recupererParId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Etudiant", "id", id));

        return ResponseEntity.ok(etudiant);
    }

    // Créer un étudiant
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Etudiant> creer(
            @Valid @RequestBody Etudiant etudiant) {

        Etudiant nouveauEtudiant = etudiantService.creer(etudiant);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nouveauEtudiant);
    }

    // Mettre à jour un étudiant
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Etudiant> mettreAJour(
            @PathVariable Long id,
            @Valid @RequestBody Etudiant etudiant) {

        Etudiant etudiantMisAJour =
                etudiantService.mettreAJour(id, etudiant);

        return ResponseEntity.ok(etudiantMisAJour);
    }

    // Supprimer un étudiant
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {

        etudiantService.supprimer(id);

        return ResponseEntity.noContent().build();
    }

    // Recherche par nom
    @GetMapping("/recherche")
    @PreAuthorize("isAuthenticated()")
    public Iterable<Etudiant> rechercherParNom(
            @RequestParam String nom) {

        return etudiantService.rechercherParNom(nom);
    }
    @GetMapping("/count")
@PreAuthorize("hasAnyRole('ADMIN','CAISSIER')")
public long countEtudiants() {

    return etudiantService.countEtudiants();
}
}
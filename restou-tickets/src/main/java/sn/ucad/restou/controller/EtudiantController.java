package sn.ucad.restou.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sn.ucad.restou.entity.Etudiant;
import sn.ucad.restou.service.EtudiantService;
import sn.ucad.restou.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    // Récupérer tous les étudiants
    @GetMapping
    public Iterable<Etudiant> recupererTous() {
        return etudiantService.recupererTous();
    }

    // Récupérer un étudiant par ID
    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> recupererParId(@PathVariable Long id) {

        Etudiant etudiant = etudiantService.recupererParId(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Etudiant", "id", id));

        return ResponseEntity.ok(etudiant);
    }

    // Créer un étudiant
    @PostMapping
    public ResponseEntity<Etudiant> creer(@Valid @RequestBody Etudiant etudiant) {

        Etudiant nouveauEtudiant = etudiantService.creer(etudiant);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nouveauEtudiant);
    }

    // Mettre à jour un étudiant
    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> mettreAJour(
            @PathVariable Long id,
            @Valid @RequestBody Etudiant etudiant) {

        Etudiant etudiantMisAJour = etudiantService.mettreAJour(id, etudiant);

        return ResponseEntity.ok(etudiantMisAJour);
    }

    // Supprimer un étudiant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {

        etudiantService.supprimer(id);

        return ResponseEntity.noContent().build();
    }

    // Recherche par nom
    @GetMapping("/recherche")
    public Iterable<Etudiant> rechercherParNom(@RequestParam String nom) {
        return etudiantService.rechercherParNom(nom);
    }
}
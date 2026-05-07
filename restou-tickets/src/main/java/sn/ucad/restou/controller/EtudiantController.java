package sn.ucad.restou.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sn.ucad.restou.entity.Etudiant;
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
    public Iterable<Etudiant> recupererTous() {
        return etudiantService.recupererTous();
    }

    // Récupérer un étudiant par ID
    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> recupererParId(@PathVariable Long id) {

        return etudiantService.recupererParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un étudiant
    @PostMapping
    public ResponseEntity<Etudiant> creer(@RequestBody Etudiant etudiant) {

        Etudiant nouveauEtudiant = etudiantService.creer(etudiant);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nouveauEtudiant);
    }

    // Mettre à jour un étudiant
    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> mettreAJour(
            @PathVariable Long id,
            @RequestBody Etudiant etudiant) {

        try {

            Etudiant etudiantMisAJour = etudiantService.mettreAJour(id, etudiant);

            return ResponseEntity.ok(etudiantMisAJour);

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un étudiant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {

        try {

            etudiantService.supprimer(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/recherche")
    public Iterable<Etudiant> rechercherParNom(@RequestParam String nom) {
        return etudiantService.rechercherParNom(nom);
    }
}
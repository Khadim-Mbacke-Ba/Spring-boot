
package sn.ucad.restou.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import sn.ucad.restou.entity.Etudiant;
import sn.ucad.restou.repository.EtudiantRepository;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    public EtudiantService(EtudiantRepository etudiantRepository) 
    {
        this.etudiantRepository = etudiantRepository;
    }
    // Créer un étudiant
    public Etudiant creer(Etudiant etudiant) 
    {
        return etudiantRepository.save(etudiant);
    }

    // Récupérer tous les étudiants
    public Iterable<Etudiant> recupererTous() {
        return etudiantRepository.findAll();
    }

    // Récupérer un étudiant par ID
    public Optional<Etudiant> recupererParId(Long id) {
        return etudiantRepository.findById(id);
    }

    // Mettre à jour un étudiant
    public Etudiant mettreAJour(Long id, Etudiant etudiantDetails) {

        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Etudiant non trouvé avec l'id : " + id));

        etudiant.setNom(etudiantDetails.getNom());
        etudiant.setPrenom(etudiantDetails.getPrenom());
        etudiant.setEmail(etudiantDetails.getEmail());
        etudiant.setNumeroCarte(etudiantDetails.getNumeroCarte());

        return etudiantRepository.save(etudiant);
    }

    // Supprimer un étudiant
    public void supprimer(Long id) {

        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Etudiant non trouvé avec l'id : " + id));

        etudiantRepository.delete(etudiant);
    }
    public Iterable<Etudiant> rechercherParNom(String nom) {
    return etudiantRepository.findByNomContaining(nom);
}
}
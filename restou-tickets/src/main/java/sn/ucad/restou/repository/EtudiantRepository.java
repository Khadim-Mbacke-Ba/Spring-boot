package sn.ucad.restou.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sn.ucad.restou.entity.Etudiant;

@Repository
public interface EtudiantRepository extends CrudRepository<Etudiant, Long> {

    Optional<Etudiant> findByEmail(String email);

    Optional<Etudiant> findByNumeroCarte(String numeroCarte);

    Iterable<Etudiant> findByNom(String nom);

    Iterable<Etudiant> findByNomContaining(String nom);
}

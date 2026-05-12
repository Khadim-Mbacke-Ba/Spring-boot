package sn.ucad.restou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.ucad.restou.entity.Utilisateur;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);
}
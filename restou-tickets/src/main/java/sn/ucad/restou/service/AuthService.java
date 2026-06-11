package sn.ucad.restou.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sn.ucad.restou.dto.AuthResponse;
import sn.ucad.restou.dto.LoginRequest;
import sn.ucad.restou.dto.RegisterRequest;
import sn.ucad.restou.entity.Role;
import sn.ucad.restou.entity.Utilisateur;
import sn.ucad.restou.repository.UtilisateurRepository;
import sn.ucad.restou.security.JwtService;

@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {

        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // Inscription
    public AuthResponse register(RegisterRequest request) {

        // Vérifier si l'email existe déjà
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        // Création de l'utilisateur
        Utilisateur utilisateur = new Utilisateur(
                request.getNom(),
                request.getPrenom(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Role.ETUDIANT);

        // Sauvegarde en base
        utilisateurRepository.save(utilisateur);

        // Génération du token JWT
String token = jwtService . generateToken ( utilisateur , utilisateur . getRole (). name ());

        // Retour de la réponse
        return new AuthResponse(
                token,
                utilisateur.getEmail(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getRole().name());
    }

    // Connexion
    public AuthResponse login(LoginRequest request) {

        // Authentification
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // Recherche de l'utilisateur
        Utilisateur utilisateur = utilisateurRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Génération du token JWT
        String token = jwtService.generateToken(utilisateur, utilisateur.getRole().name());

        // Retour de la réponse
        return new AuthResponse(
                token,
                utilisateur.getEmail(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getRole().name());
    }
}
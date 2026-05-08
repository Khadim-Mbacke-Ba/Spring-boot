package sn.ucad.restou.service;

import org.springframework.stereotype.Service;
import sn.ucad.restou.entity.Ticket;
import sn.ucad.restou.exception.ResourceNotFoundException;
import sn.ucad.restou.repository.TicketRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // CREATE
    public Ticket creer(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // READ ALL
    public Iterable<Ticket> recupererTous() {
        return ticketRepository.findAll();
    }

    // READ BY ID
    public Optional<Ticket> recupererParId(Long id) {
        return ticketRepository.findById(id);
    }

    // READ BY STATUT
    public Iterable<Ticket> recupererParStatut(Boolean utilise) {
        return ticketRepository.findByUtilise(utilise);
    }

    // READ BY ETUDIANT
    public Iterable<Ticket> recupererParEtudiant(Long etudiantId) {
        return ticketRepository.findByEtudiantId(etudiantId);
    }

    // VALIDATION TICKET (EXERCICE 1)
    public Ticket valider(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", id));

        // ❌ Exercice 1 : ticket expiré interdit
        if (ticket.getDateValidite().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Ce ticket est expiré");
        }

        // déjà utilisé
        if (ticket.getUtilise()) {
            throw new IllegalStateException("Ce ticket a déjà été utilisé");
        }

        ticket.setUtilise(true);

        return ticketRepository.save(ticket);
    }

    // DELETE
    public void supprimer(Long id) {
        ticketRepository.deleteById(id);
    }

    // STATISTIQUES
    public Map<String, Long> statistiques() {

        Iterable<Ticket> tickets = ticketRepository.findAll();

        long total = 0;
        long utilises = 0;
        long disponibles = 0;

        for (Ticket ticket : tickets) {
            total++;

            if (ticket.getUtilise()) {
                utilises++;
            } else {
                disponibles++;
            }
        }

        Map<String, Long> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("utilises", utilises);
        stats.put("disponibles", disponibles);

        return stats;
    }
}
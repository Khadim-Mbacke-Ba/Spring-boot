package sn.ucad.restou.service;

import org.springframework.stereotype.Service;
import sn.ucad.restou.entity.Ticket;
import sn.ucad.restou.repository.TicketRepository;

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

    // VALIDATION TICKET
    public Ticket valider(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket non trouvé"));

        if (ticket.getUtilise()) {
            throw new RuntimeException("Ce ticket a déjà été utilisé !");
        }

        ticket.setUtilise(true);

        return ticketRepository.save(ticket);
    }

    // DELETE
    public void supprimer(Long id) {
        ticketRepository.deleteById(id);
    }
}
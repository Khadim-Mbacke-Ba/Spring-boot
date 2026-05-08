package sn.ucad.restou.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sn.ucad.restou.entity.Ticket;
import sn.ucad.restou.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // GET ALL
    @GetMapping
    public Iterable<Ticket> recupererTous() {
        return ticketService.recupererTous();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> recupererParId(@PathVariable Long id) {
        return ticketService.recupererParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET BY ETUDIANT
    @GetMapping("/etudiant/{etudiantId}")
    public Iterable<Ticket> recupererParEtudiant(@PathVariable Long etudiantId) {
        return ticketService.recupererParEtudiant(etudiantId);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Ticket> creer(@RequestBody Ticket ticket) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticketService.creer(ticket));
    }

    // VALIDATE TICKET
    @PatchMapping("/{id}/valider")
    public ResponseEntity<?> valider(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ticketService.valider(id));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        ticketService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<?> statistiques() {
        return ResponseEntity.ok(ticketService.statistiques());
    }
}
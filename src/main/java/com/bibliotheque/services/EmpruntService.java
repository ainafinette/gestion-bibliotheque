package com.bibliotheque.services;

import com.bibliotheque.depots.EmpruntDepot;
import com.bibliotheque.entites.Client;
import com.bibliotheque.entites.Emprunt;
import com.bibliotheque.entites.Livre;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpruntService {

    private final EmpruntDepot empruntDepot;
    private final ClientService clientService;
    private final LivreService livreService;

    public EmpruntService(EmpruntDepot empruntDepot, ClientService clientService, LivreService livreService) {
        this.empruntDepot = empruntDepot;
        this.clientService = clientService;
        this.livreService = livreService;
    }

    public List<Emprunt> listerTous() {
        return empruntDepot.findAll();
    }

    public List<Emprunt> listerEnCours() {
        return empruntDepot.findByDateRetourIsNull();
    }

    public Emprunt trouverParId(Long id) {
        return empruntDepot.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Emprunt introuvable avec l'id " + id));
    }

    public Emprunt emprunter(Long clientId, Long livreId) {
        Client client = clientService.trouverParId(clientId);
        Livre livre = livreService.trouverParId(livreId);

        if (!livre.isDisponible()) {
            throw new IllegalStateException("Ce livre n'est pas disponible");
        }

        livre.setDisponible(false);
        livreService.enregistrer(livre);

        Emprunt emprunt = new Emprunt(client, livre, LocalDate.now());
        return empruntDepot.save(emprunt);
    }

    public void retourner(Long empruntId) {
        Emprunt emprunt = trouverParId(empruntId);

        if (emprunt.estRendu()) {
            throw new IllegalStateException("Ce livre a deja ete rendu");
        }

        emprunt.setDateRetour(LocalDate.now());
        empruntDepot.save(emprunt);

        Livre livre = emprunt.getLivre();
        livre.setDisponible(true);
        livreService.enregistrer(livre);
    }
}

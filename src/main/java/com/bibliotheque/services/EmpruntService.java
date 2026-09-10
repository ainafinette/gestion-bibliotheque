package com.bibliotheque.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bibliotheque.depots.EmpruntDepot;
import com.bibliotheque.entites.Client;
import com.bibliotheque.entites.Emprunt;
import com.bibliotheque.entites.Livre;

@Service
public class EmpruntService {

    private final EmpruntDepot empruntDepot;
    private final ClientService clientService;
    private final LivreService livreService;
     private final HistoriqueService historiqueService;  

    public EmpruntService(EmpruntDepot empruntDepot, ClientService clientService, LivreService livreService,HistoriqueService historiqueService) {
        this.empruntDepot = empruntDepot;
        this.clientService = clientService;
        this.livreService = livreService;
       this.historiqueService = historiqueService;
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
         historiqueService.enregistrerComplet(
                "EMPRUNT",
                "Livre '" + livre.getNom() + "' emprunté par " + client.getNom(),
                client,
                livre
        );

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

        historiqueService.enregistrerComplet(
                "RETOUR",
                "Livre '" + livre.getNom() + "' retourné par " + emprunt.getClient().getNom(),
                emprunt.getClient(),
                livre
        );
    }
}

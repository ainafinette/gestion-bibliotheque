package com.bibliotheque.services;

import com.bibliotheque.depots.HistoriqueDepot;
import com.bibliotheque.entites.Client;
import com.bibliotheque.entites.Historique;
import com.bibliotheque.entites.Livre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoriqueService {

    @Autowired
    private HistoriqueDepot historiqueDepot;

    // Enregistrer une action
    public void enregistrer(String action, String description) {
        Historique h = new Historique(action, description);
        historiqueDepot.save(h);
    }

    // Enregistrer avec client
    public void enregistrerAvecClient(String action, String description, Client client) {
        Historique h = new Historique(action, description);
        h.setClient(client);
        historiqueDepot.save(h);
    }

    // Enregistrer avec livre
    public void enregistrerAvecLivre(String action, String description, Livre livre) {
        Historique h = new Historique(action, description);
        h.setLivre(livre);
        historiqueDepot.save(h);
    }

    // Enregistrer avec client et livre
    public void enregistrerComplet(String action, String description, Client client, Livre livre) {
        Historique h = new Historique(action, description);
        h.setClient(client);
        h.setLivre(livre);
        historiqueDepot.save(h);
    }

    // Lister tout l'historique (du plus récent au plus ancien)
    public List<Historique> listerTout() {
        return historiqueDepot.findAll(
            org.springframework.data.domain.Sort.by(
                org.springframework.data.domain.Sort.Direction.DESC, "dateAction"));
    }

    // Dernières actions
    public List<Historique> dernieresActions() {
        return historiqueDepot.findTop10ByOrderByDateActionDesc();
    }

    // Par type d'action
    public List<Historique> parAction(String action) {
        return historiqueDepot.findByActionOrderByDateActionDesc(action);
    }

    // Par période
    public List<Historique> parPeriode(LocalDateTime debut, LocalDateTime fin) {
        return historiqueDepot.findByDateActionBetweenOrderByDateActionDesc(debut, fin);
    }

    // Par client
    public List<Historique> parClient(Long clientId) {
        return historiqueDepot.findByClientIdOrderByDateActionDesc(clientId);
    }

    // Compter le total
    public long total() {
        return historiqueDepot.count();
    }
}
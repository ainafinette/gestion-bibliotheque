package com.bibliotheque.services;

import com.bibliotheque.depots.LivreDepot;
import com.bibliotheque.entites.Livre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreService {

    private final LivreDepot livreDepot;

    public LivreService(LivreDepot livreDepot) {
        this.livreDepot = livreDepot;
    }

    public List<Livre> listerTous() {
        return livreDepot.findAll();
    }

    public List<Livre> listerDisponibles() {
        return livreDepot.findByDisponibleTrue();
    }

    public Livre trouverParId(Long id) {
        return livreDepot.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livre introuvable avec l'id " + id));
    }

    public Livre enregistrer(Livre livre) {
        return livreDepot.save(livre);
    }

    public void supprimer(Long id) {
        livreDepot.deleteById(id);
    }
}

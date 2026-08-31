package com.bibliotheque.services;

import com.bibliotheque.depots.ClientDepot;
import com.bibliotheque.entites.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientDepot clientDepot;

    public ClientService(ClientDepot clientDepot) {
        this.clientDepot = clientDepot;
    }

    public List<Client> listerTous() {
        return clientDepot.findAll();
    }

    public Client trouverParId(Long id) {
        return clientDepot.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable avec l'id " + id));
    }

    public Client enregistrer(Client client) {
        return clientDepot.save(client);
    }

    public void supprimer(Long id) {
        clientDepot.deleteById(id);
    }
}

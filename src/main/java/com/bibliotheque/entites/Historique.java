package com.bibliotheque.entites;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historiques")
public class Historique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action; // EX: "EMPRUNT", "RETOUR", "AJOUT_LIVRE", "SUPPRESSION"

    @Column(nullable = false)
    private String description;

    @Column(name = "date_action", nullable = false)
    private LocalDateTime dateAction;

    // Optionnel : lier à un client
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // Optionnel : lier à un livre
    @ManyToOne
    @JoinColumn(name = "livre_id")
    private Livre livre;

    // Constructeurs
    public Historique() {
        this.dateAction = LocalDateTime.now();
    }

    public Historique(String action, String description) {
        this.action = action;
        this.description = description;
        this.dateAction = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDateAction() { return dateAction; }
    public void setDateAction(LocalDateTime dateAction) { this.dateAction = dateAction; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }
}
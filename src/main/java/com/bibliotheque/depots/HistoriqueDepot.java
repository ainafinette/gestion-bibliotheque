package com.bibliotheque.depots;

import com.bibliotheque.entites.Historique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoriqueDepot extends JpaRepository<Historique, Long> {

    // Trouver par type d'action
    List<Historique> findByActionOrderByDateActionDesc(String action);

    // Trouver les actions récentes
    List<Historique> findTop10ByOrderByDateActionDesc();

    // Trouver par période
    List<Historique> findByDateActionBetweenOrderByDateActionDesc(
        LocalDateTime debut, LocalDateTime fin);

    // Trouver par client
    List<Historique> findByClientIdOrderByDateActionDesc(Long clientId);

    // Trouver par livre
    List<Historique> findByLivreIdOrderByDateActionDesc(Long livreId);
}
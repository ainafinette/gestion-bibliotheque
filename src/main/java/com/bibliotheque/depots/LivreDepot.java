package com.bibliotheque.depots;

import com.bibliotheque.entites.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivreDepot extends JpaRepository<Livre, Long> {

    List<Livre> findByDisponibleTrue();
}

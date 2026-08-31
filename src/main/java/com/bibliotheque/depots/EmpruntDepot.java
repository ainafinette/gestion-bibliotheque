package com.bibliotheque.depots;

import com.bibliotheque.entites.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpruntDepot extends JpaRepository<Emprunt, Long> {

    List<Emprunt> findByDateRetourIsNull();
}

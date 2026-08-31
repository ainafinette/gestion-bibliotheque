package com.bibliotheque.depots;

import com.bibliotheque.entites.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDepot extends JpaRepository<Client, Long> {
}

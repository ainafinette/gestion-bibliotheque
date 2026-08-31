package com.bibliotheque.services;

import com.bibliotheque.depots.EmpruntDepot;
import com.bibliotheque.entites.Client;
import com.bibliotheque.entites.Emprunt;
import com.bibliotheque.entites.Livre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpruntServiceTest {

    @Mock
    private EmpruntDepot empruntDepot;

    @Mock
    private ClientService clientService;

    @Mock
    private LivreService livreService;

    @InjectMocks
    private EmpruntService empruntService;

    private Client client;
    private Livre livre;

    @BeforeEach
    void initialiser() {
        client = new Client("Rakoto", "0341234567");
        client.setId(1L);

        livre = new Livre("Madagascar, une histoire", "Solofo Randrianja");
        livre.setId(1L);
        livre.setDisponible(true);
    }

    @Test
    void emprunter_rendIndisponibleUnLivreDisponible() {
        when(clientService.trouverParId(1L)).thenReturn(client);
        when(livreService.trouverParId(1L)).thenReturn(livre);
        when(empruntDepot.save(any(Emprunt.class))).thenAnswer(appel -> appel.getArgument(0));

        Emprunt resultat = empruntService.emprunter(1L, 1L);

        assertThat(resultat.getClient()).isEqualTo(client);
        assertThat(resultat.getLivre()).isEqualTo(livre);
        assertThat(resultat.getDateEmprunt()).isEqualTo(LocalDate.now());
        assertThat(livre.isDisponible()).isFalse();
        verify(livreService).enregistrer(livre);
    }

    @Test
    void emprunter_refuseSiLivreDejaIndisponible() {
        livre.setDisponible(false);
        when(clientService.trouverParId(1L)).thenReturn(client);
        when(livreService.trouverParId(1L)).thenReturn(livre);

        assertThatThrownBy(() -> empruntService.emprunter(1L, 1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("n'est pas disponible");
    }

    @Test
    void retourner_rendDisponibleLeLivreEtFixeLaDateDeRetour() {
        livre.setDisponible(false);
        Emprunt emprunt = new Emprunt(client, livre, LocalDate.now().minusDays(3));
        emprunt.setId(5L);
        when(empruntDepot.findById(5L)).thenReturn(Optional.of(emprunt));

        empruntService.retourner(5L);

        assertThat(emprunt.getDateRetour()).isEqualTo(LocalDate.now());
        assertThat(livre.isDisponible()).isTrue();
        verify(livreService).enregistrer(livre);
        verify(empruntDepot).save(emprunt);
    }

    @Test
    void retourner_refuseSiEmpruntDejaRendu() {
        Emprunt emprunt = new Emprunt(client, livre, LocalDate.now().minusDays(5));
        emprunt.setId(6L);
        emprunt.setDateRetour(LocalDate.now().minusDays(1));
        when(empruntDepot.findById(6L)).thenReturn(Optional.of(emprunt));

        assertThatThrownBy(() -> empruntService.retourner(6L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("deja ete rendu");
    }

    @Test
    void trouverParId_leveUneExceptionSiEmpruntInexistant() {
        when(empruntDepot.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> empruntService.trouverParId(99L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

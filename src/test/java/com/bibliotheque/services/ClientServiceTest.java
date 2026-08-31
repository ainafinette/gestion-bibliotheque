package com.bibliotheque.services;

import com.bibliotheque.depots.ClientDepot;
import com.bibliotheque.entites.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientDepot clientDepot;

    @InjectMocks
    private ClientService clientService;

    @Test
    void listerTous_retourneLaListeDuDepot() {
        Client client = new Client("Rasoa", "0331112233");
        when(clientDepot.findAll()).thenReturn(List.of(client));

        List<Client> resultat = clientService.listerTous();

        assertThat(resultat).containsExactly(client);
    }

    @Test
    void trouverParId_retourneLeClientQuandIlExiste() {
        Client client = new Client("Rasoa", "0331112233");
        client.setId(1L);
        when(clientDepot.findById(1L)).thenReturn(Optional.of(client));

        Client resultat = clientService.trouverParId(1L);

        assertThat(resultat).isEqualTo(client);
    }

    @Test
    void trouverParId_leveUneExceptionSiClientInexistant() {
        when(clientDepot.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.trouverParId(2L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void enregistrer_appelleLeDepot() {
        Client client = new Client("Rasoa", "0331112233");
        when(clientDepot.save(client)).thenReturn(client);

        clientService.enregistrer(client);

        verify(clientDepot).save(client);
    }

    @Test
    void supprimer_appelleLeDepot() {
        clientService.supprimer(1L);

        verify(clientDepot).deleteById(1L);
    }
}

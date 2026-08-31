package com.bibliotheque.services;

import com.bibliotheque.depots.LivreDepot;
import com.bibliotheque.entites.Livre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LivreServiceTest {

    @Mock
    private LivreDepot livreDepot;

    @InjectMocks
    private LivreService livreService;

    @Test
    void listerDisponibles_neRetourneQueLesLivresDisponibles() {
        Livre livre = new Livre("Le petit prince", "Antoine de Saint-Exupery");
        when(livreDepot.findByDisponibleTrue()).thenReturn(List.of(livre));

        List<Livre> resultat = livreService.listerDisponibles();

        assertThat(resultat).containsExactly(livre);
    }

    @Test
    void nouveauLivre_estDisponibleParDefaut() {
        Livre livre = new Livre("1984", "George Orwell");

        assertThat(livre.isDisponible()).isTrue();
    }

    @Test
    void trouverParId_leveUneExceptionSiLivreInexistant() {
        when(livreDepot.findById(3L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> livreService.trouverParId(3L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

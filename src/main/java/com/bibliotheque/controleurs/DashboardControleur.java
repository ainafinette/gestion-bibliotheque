package com.bibliotheque.controleurs;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bibliotheque.depots.ClientDepot;
import com.bibliotheque.depots.EmpruntDepot;
import com.bibliotheque.depots.LivreDepot;
import com.bibliotheque.entites.Emprunt;
import com.bibliotheque.services.HistoriqueService;

@Controller
public class DashboardControleur {

    private final LivreDepot livreDepot;
    private final ClientDepot clientDepot;
    private final EmpruntDepot empruntDepot;
    private final HistoriqueService historiqueService;

    public DashboardControleur(LivreDepot livreDepot,
                                ClientDepot clientDepot,
                                EmpruntDepot empruntDepot,
                                HistoriqueService historiqueService) {
        this.livreDepot = livreDepot;
        this.clientDepot = clientDepot;
        this.empruntDepot = empruntDepot;
        this.historiqueService = historiqueService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
     
        model.addAttribute("totalLivres", livreDepot.count());
        model.addAttribute("totalClients", clientDepot.count());
        model.addAttribute("totalEmprunts", empruntDepot.count());
        model.addAttribute("livresDisponibles",
                livreDepot.findAll().stream().filter(l -> l.isDisponible()).count());
        model.addAttribute("empruntsEnCours",
                empruntDepot.findAll().stream().filter(e -> e.getDateRetour() == null).count());

     
        model.addAttribute("historiques", historiqueService.listerTout());

       
        List<Emprunt> emprunts = empruntDepot.findAll();
        model.addAttribute("derniersEmprunts", emprunts);

        return "dashboard";
    }
}
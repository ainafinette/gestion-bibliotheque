package com.bibliotheque.controleurs;

import com.bibliotheque.services.ClientService;
import com.bibliotheque.services.EmpruntService;
import com.bibliotheque.services.LivreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/emprunts")
public class EmpruntControleur {

    private final EmpruntService empruntService;
    private final ClientService clientService;
    private final LivreService livreService;

    public EmpruntControleur(EmpruntService empruntService, ClientService clientService, LivreService livreService) {
        this.empruntService = empruntService;
        this.clientService = clientService;
        this.livreService = livreService;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("emprunts", empruntService.listerTous());
        return "emprunts/liste";
    }

    @GetMapping("/nouveau")
    public String formulaire(Model model) {
        model.addAttribute("clients", clientService.listerTous());
        model.addAttribute("livres", livreService.listerDisponibles());
        return "emprunts/formulaire";
    }

    @PostMapping("/enregistrer")
    public String emprunter(@RequestParam Long clientId, @RequestParam Long livreId,
                             RedirectAttributes redirectAttributes) {
        try {
            empruntService.emprunter(clientId, livreId);
        } catch (IllegalStateException erreur) {
            redirectAttributes.addFlashAttribute("erreur", erreur.getMessage());
            return "redirect:/emprunts/nouveau";
        }
        return "redirect:/emprunts";
    }

    @GetMapping("/retourner/{id}")
    public String retourner(@PathVariable Long id) {
        empruntService.retourner(id);
        return "redirect:/emprunts";
    }
}

package com.bibliotheque.controleurs;

import com.bibliotheque.entites.Client;
import com.bibliotheque.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clients")
public class ClientControleur {

    private final ClientService clientService;

    public ClientControleur(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("clients", clientService.listerTous());
        return "clients/liste";
    }

    @GetMapping("/nouveau")
    public String formulaireAjout(Model model) {
        model.addAttribute("client", new Client());
        return "clients/formulaire";
    }

    @GetMapping("/modifier/{id}")
    public String formulaireModification(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.trouverParId(id));
        return "clients/formulaire";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(@Valid @ModelAttribute Client client, BindingResult resultat) {
        if (resultat.hasErrors()) {
            return "clients/formulaire";
        }
        clientService.enregistrer(client);
        return "redirect:/clients";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        clientService.supprimer(id);
        return "redirect:/clients";
    }
}

package com.bibliotheque.controleurs;

import com.bibliotheque.entites.Livre;
import com.bibliotheque.services.LivreService;
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
@RequestMapping("/livres")
public class LivreControleur {

    private final LivreService livreService;

    public LivreControleur(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("livres", livreService.listerTous());
        return "livres/liste";
    }

    @GetMapping("/nouveau")
    public String formulaireAjout(Model model) {
        model.addAttribute("livre", new Livre());
        return "livres/formulaire";
    }

    @GetMapping("/modifier/{id}")
    public String formulaireModification(@PathVariable Long id, Model model) {
        model.addAttribute("livre", livreService.trouverParId(id));
        return "livres/formulaire";
    }

    @PostMapping("/enregistrer")
    public String enregistrer(@Valid @ModelAttribute Livre livre, BindingResult resultat) {
        if (resultat.hasErrors()) {
            return "livres/formulaire";
        }
        livreService.enregistrer(livre);
        return "redirect:/livres";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        livreService.supprimer(id);
        return "redirect:/livres";
    }
}

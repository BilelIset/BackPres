package tn.isetsf.presence.webThymeleaf;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;

@Controller
@Transactional
public class EnseignantController {
    @GetMapping(path = "/EspaceEnseignant")
    public String EnseignantZone(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String utilisateur="";


        if(authentication!=null){
             utilisateur=((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("user",utilisateur);

            System.out.println("Utilisateur connecté : "+utilisateur);
        }
        model.addAttribute("message","Hello Teacher");
        model.addAttribute("user",utilisateur);
        return "EspaceEnseignant";

    }
}

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

        if(authentication!=null){
            Static.CURRENT_USER=((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("user",Static.CURRENT_USER);

            System.out.println("Utilisateur connecté : "+Static.CURRENT_USER);
        }
        model.addAttribute("message","Hello Teacher");
        model.addAttribute("user",Static.CURRENT_USER);
        return "EspaceEnseignant";

    }
}

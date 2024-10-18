package tn.isetsf.presence.sec.webSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.isetsf.presence.sec.entity.Logged;
import tn.isetsf.presence.sec.repository.LoggedRepo;
import tn.isetsf.presence.webThymeleaf.Static;

import java.time.LocalDate;

@Controller
@RequestMapping("/default")
public class DefaultController {
    @Autowired
LoggedRepo loggedRepo;

    @GetMapping
    public String defaultAfterLogin(Authentication authentication) {



        if(authentication!=null){
            System.out.println("Authentification recu ....");
           String CURRENT_USER=((UserDetails) authentication.getPrincipal()).getUsername();
            System.out.println("Utilisateur recu ...."+CURRENT_USER);
            String CURRENT_ROLE= ((UserDetails) authentication.getPrincipal()).getAuthorities().toString();
            System.out.println("Roles recu ...."+CURRENT_ROLE);
            Logged logged=new Logged(null,CURRENT_USER,CURRENT_ROLE,true, LocalDate.now(),null);
            System.out.println("Logged recu ...."+logged);
            loggedRepo.save(logged);
            System.out.println("Succeed saving  ...."+CURRENT_USER);


        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            System.out.println("Entree et redirection vers dashboard ....");
            return "redirect:/Dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ENSEIGNANT"))) {
            System.out.println("Entree et redirection vers Enseignant ....");

            return "redirect:/EspaceEnseignant";
        }
        return "redirect:/";
    }
}

package tn.isetsf.presence.webThymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tn.isetsf.presence.sec.config.SecurityConfig;
import tn.isetsf.presence.sec.entity.Logged;
import tn.isetsf.presence.sec.repository.LoggedRepo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Controller
@Transactional
public class LoginController {
    @Autowired
    LoggedRepo loggedRepo;

    @GetMapping(path = "/login")
    public String logUser(Model model){
        return "login"; // Retirer le slash ici
    }

    @GetMapping(path = "/deconnect")
    public String logOutUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("Authentification reçue ....");
            String CURRENT_USER = ((UserDetails) authentication.getPrincipal()).getUsername();
            System.out.println("Utilisateur reçu .... " + CURRENT_USER);
            Logged logged=loggedRepo.getByLogName(CURRENT_USER);

            // Vous pouvez ajouter une logique ici pour enregistrer la déconnexion
            System.out.println(logged);
            loggedRepo.deleteById(logged.getId());
            System.out.println("Suppression effectuer avec success .... ");
            // Invalider la session
            request.getSession().invalidate();
            SecurityContextHolder.clearContext();

            // Optionnel : ajouter un message à afficher après la déconnexion
            request.getSession().setAttribute("message", "Déconnexion réussie");
        }

        // Rediriger vers la page de connexion ou une autre page
        return "redirect:/login"; // Redirection vers la page de connexion
    }

}


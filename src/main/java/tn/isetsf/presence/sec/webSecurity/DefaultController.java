package tn.isetsf.presence.sec.webSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.WebSession;
import org.springframework.web.server.session.InMemoryWebSessionStore;
import tn.isetsf.presence.PresenceApplication;
import tn.isetsf.presence.sec.entity.Logged;
import tn.isetsf.presence.sec.repository.LoggedRepo;

import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;@Controller
@RequestMapping("/default")
public class DefaultController {

    @Autowired
    private LoggedRepo loggedRepo;

    @GetMapping
    public String defaultAfterLogin(Authentication authentication, HttpSession httpSession) {
        String currentUser = getCurrentUser(authentication);
        updateLoggedUsersStatus(currentUser);

        String sessionId = httpSession.getId();
        if (currentUser != null && sessionId != null) {
            Logged loggedRecord = new Logged(null, currentUser, authentication.getAuthorities().toString(), true, LocalDateTime.now(), null, sessionId);
            if (loggedRepo.findByLogNameAndSessionId(currentUser, sessionId).isEmpty()) {
                loggedRepo.save(loggedRecord);
            }
            return redirectToRolePage(authentication);
        }
        return "redirect:/";
    }

    private String getCurrentUser(Authentication authentication) {
        return (authentication != null) ? ((UserDetails) authentication.getPrincipal()).getUsername() : null;
    }

    private void updateLoggedUsersStatus(String currentUser) {
        if (!loggedRepo.findByConnectedTrue().isEmpty()) {
            loggedRepo.findByConnectedTrue().forEach(loggedUser -> {
                if (loggedUser.getLogName().equals(currentUser)) {
                    loggedUser.setConnected(false);
                    loggedUser.setDateDeconnect(LocalDateTime.now());
                }
                loggedRepo.save(loggedUser);
            });
        }
    }

    private String redirectToRolePage(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/Dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ENSEIGNANT"))) {
            return "redirect:/EspaceEnseignant";
        }
        return "redirect:/";
    }
}

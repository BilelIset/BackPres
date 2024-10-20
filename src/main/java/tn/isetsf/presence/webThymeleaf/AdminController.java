package tn.isetsf.presence.webThymeleaf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.session.InMemoryWebSessionStore;
import tn.isetsf.presence.CalculDate;
import tn.isetsf.presence.Entity.LigneAbsence;
import tn.isetsf.presence.Repository.EnstRepo;
import tn.isetsf.presence.Repository.LigneAbsenceRepo;
import tn.isetsf.presence.Repository.SalleRepo;
import tn.isetsf.presence.sec.entity.Logged;
import tn.isetsf.presence.sec.repository.LoggedRepo;
import tn.isetsf.presence.serviceMail.EmailService;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Controller
@Transactional
@RolesAllowed("ADMIN")
public class AdminController {
    @Autowired
    LoggedRepo loggedRepo;




    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    private final LigneAbsenceRepo ligneAbsenceRepo;
    private final EnstRepo enstRepo;
    private final SalleRepo salleRepo;
    private final ServerStatusService serverStatusService;
    private final CalculDate calculDate = new CalculDate();

    public AdminController( LigneAbsenceRepo ligneAbsenceRepo, EnstRepo enstRepo, SalleRepo salleRepo, ServerStatusService serverStatusService) {
        this.ligneAbsenceRepo = ligneAbsenceRepo;
        this.enstRepo = enstRepo;
        this.salleRepo = salleRepo;
        this.serverStatusService = serverStatusService;
    }



    @RolesAllowed("ADMIN")
//    @GetMapping(path = "/forcerDeconnect")
//    public String forcedDeconnect(Model model,){
//        System.out.println("Session recu : "+session + "Login recu : "+id);
//        return " redirect:/login";
//
//    }

    @GetMapping(path = "/AbsenceEnseignant")
    public String indexModel(Model model,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "5") int size,
                             @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        Page<LigneAbsence> absencePage = ligneAbsenceRepo.findByEnsi1Contains(keyword, PageRequest.of(page, size));
        logger.info("Total pages: {} | Total elements: {}", absencePage.getTotalPages(), absencePage.getTotalElements());

        model.addAttribute("listAbsence", absencePage.getContent());
        model.addAttribute("pages", new int[absencePage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pathCourant", "/AbsenceEnseignant");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String utilisateur = "";

        if (authentication != null) {
            utilisateur = ((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("user", utilisateur);
            logger.info("Utilisateur connecté : {}", utilisateur);
        }

        model.addAttribute("user", utilisateur);

        return "AbsenceEnseignant"; // Ensure this returns the correct view name
    }

    @RolesAllowed("ADMIN")

    @GetMapping(path = "/AbsenceEtudiant")
    public String etudiantPage(Model model) {
        model.addAttribute("pathCourant", "/AbsenceEtudiant");
        //String utilisateurCourant = "";
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String utilisateur="";


        if(authentication!=null){
            utilisateur=((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("user",utilisateur);

            System.out.println("Utilisateur connecté : "+utilisateur);
        }
        model.addAttribute("user",utilisateur);

        return "AbsenceEtudiant"; // Ensure this returns the correct view name
    }
    @RolesAllowed("ADMIN")

    @GetMapping(path = "/Dashboard")
    public String dashboardModel(Model model,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 @RequestParam(value = "keyword", defaultValue = "") String keyword) throws IOException {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String utilisateur="";


        if(authentication!=null){
            utilisateur=((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("user",utilisateur);

            System.out.println("Utilisateur connecté : "+utilisateur);
        }

        model.addAttribute("user",utilisateur);
List<Logged> loggedList=loggedRepo.findByConnectedTrue();
System.out.println("Utilisateur trouvé a la connection"+loggedList);
System.out.println("Utilisateur trouvé apres l'erreur"+loggedList);
System.out.println(loggedList);
model.addAttribute("loggedList",loggedList);
        int notifiedCount = 0;
        Page<LigneAbsence> absencePage = ligneAbsenceRepo.findByEnsi1Contains(keyword, PageRequest.of(page, size));

        for (LigneAbsence ligneAbsence : absencePage) {
            if (ligneAbsence.getNotified()) {
                notifiedCount++;
            }
        }

boolean mobileServer = serverStatusService.checkMobileAppServerStatus("https://www.apirest.tech/emploi/all");
        System.out.println("Mobile "+mobileServer);
        boolean mailServer=true;// = serverStatusService.checkMailServer();
        System.out.println("Mail "+mailServer);
        boolean webServer = serverStatusService.checkMobileAppServerStatus("https://www.apirest.tech/");
        System.out.println("Web "+webServer);
        boolean statusGeneral = mailServer && mobileServer && webServer;
        System.out.println("Générale  "+statusGeneral);

        model.addAttribute("statusGeneral", statusGeneral);
        model.addAttribute("webServer", webServer);
        model.addAttribute("mailServer", mailServer);
        model.addAttribute("mobileServer", mobileServer);

        logger.info("Mobile server status: {}", mobileServer);

        Long nbEns = enstRepo.count();
        Long nbSalles = salleRepo.count();
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateNow = formatter.format(date);
        String fullDate = calculDate.JourEnTouteLettre() + "  " + dateNow;

        List<LigneAbsence> listNonNotified = ligneAbsenceRepo.findByNotifiedFalse();
        List<LigneAbsence> listNonNotifier=new ArrayList<>();
        for (int i=0;i<5;i++)
        {
            listNonNotifier.add(listNonNotified.get(i));
        }
        model.addAttribute("listNonNotifier", listNonNotifier);
        model.addAttribute("dateNow", fullDate);
        model.addAttribute("nbEns", nbEns);
        model.addAttribute("nbSalles", nbSalles);
        model.addAttribute("listAbsence", absencePage.getContent());
        model.addAttribute("pages", new int[absencePage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pathCourant", "/Dashboard");
        model.addAttribute("nbNotifier", notifiedCount);


        List<Object[]> objectList = ligneAbsenceRepo.countAbsencesByEnseignantNative();
        model.addAttribute("absenceByEnseignant", objectList);

        return "Dashboard"; // Ensure this returns the correct view name
    }
    @GetMapping("/api/absences")
    @ResponseBody
    public List<Object[]> getAbsencesByEnseignant() {
        List<Object[]> objectList = ligneAbsenceRepo.countAbsencesByEnseignantNative();
        List<Object[]> objects=new ArrayList<>();
        for (int i=0;i<5;i++){
            objects.add(objectList.get(i));
        }




    return objects;

}}


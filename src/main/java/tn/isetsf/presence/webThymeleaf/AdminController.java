package tn.isetsf.presence.webThymeleaf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tn.isetsf.presence.CalculDate;
import tn.isetsf.presence.Entity.LigneAbsence;
import tn.isetsf.presence.Repository.EnstRepo;
import tn.isetsf.presence.Repository.LigneAbsenceRepo;
import tn.isetsf.presence.Repository.SalleRepo;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Transactional
@RolesAllowed("ADMIN")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);



    private final LigneAbsenceRepo ligneAbsenceRepo;
    private final EnstRepo enstRepo;
    private final SalleRepo salleRepo;
    private final ServerStatusService serverStatusService;
    private final CalculDate calculDate = new CalculDate();

    public AdminController(LigneAbsenceRepo ligneAbsenceRepo, EnstRepo enstRepo, SalleRepo salleRepo, ServerStatusService serverStatusService) {
        this.ligneAbsenceRepo = ligneAbsenceRepo;
        this.enstRepo = enstRepo;
        this.salleRepo = salleRepo;
        this.serverStatusService = serverStatusService;
    }



    @RolesAllowed("ADMIN")

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
        model.addAttribute("user",Static.CURRENT_USER);


        return "/AbsenceEnseignant"; // Ensure this returns the correct view name
    }
    @RolesAllowed("ADMIN")

    @GetMapping(path = "/AbsenceEtudiant")
    public String etudiantPage(Model model) {
        model.addAttribute("pathCourant", "/AbsenceEtudiant");
        model.addAttribute("user",Static.CURRENT_USER);

        return "/AbsenceEtudiant"; // Ensure this returns the correct view name
    }
    @RolesAllowed("ADMIN")

    @GetMapping(path = "/Dashboard")
    public String dashboardModel(Model model,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(authentication!=null){
             Static.CURRENT_USER=((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("user",Static.CURRENT_USER);

            System.out.println("Utilisateur connecté : "+Static.CURRENT_USER);
        }
        int notifiedCount = 0;
        Page<LigneAbsence> absencePage = ligneAbsenceRepo.findByEnsi1Contains(keyword, PageRequest.of(page, size));

        for (LigneAbsence ligneAbsence : absencePage) {
            if (ligneAbsence.getNotified()) {
                notifiedCount++;
            }
        }
boolean mobileServer = serverStatusService.checkMobileAppServerStatus("https://back.apirest.tech/emploi/all");
        System.out.println("Mobile "+mobileServer);
        boolean mailServer = serverStatusService.checkMailServer("https://back.apirest.tech/actuator/health/mail");
        System.out.println("Mail "+mailServer);
        boolean webServer = serverStatusService.checkMobileAppServerStatus("https://back.apirest.tech/");
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

        List<LigneAbsence> listNonNotifier = ligneAbsenceRepo.findByNotifiedFalse();
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

        return "/Dashboard"; // Ensure this returns the correct view name
    }
}


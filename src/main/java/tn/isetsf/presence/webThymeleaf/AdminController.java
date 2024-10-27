package tn.isetsf.presence.webThymeleaf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.session.InMemoryWebSessionStore;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import tn.isetsf.presence.CalculDate;
import tn.isetsf.presence.Entity.LigneAbsence;
import tn.isetsf.presence.Repository.EnstRepo;
import tn.isetsf.presence.Repository.LigneAbsenceRepo;
import tn.isetsf.presence.Repository.SalleRepo;
import tn.isetsf.presence.sec.entity.AppRole;
import tn.isetsf.presence.sec.entity.AppUser;
import tn.isetsf.presence.sec.entity.Logged;
import tn.isetsf.presence.sec.repository.AppRoleRepo;
import tn.isetsf.presence.sec.repository.AppUserRepo;
import tn.isetsf.presence.sec.repository.LoggedRepo;
import tn.isetsf.presence.sec.service.AppUserInterfaceImpl;
import tn.isetsf.presence.serviceMail.EmailService;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@Transactional
@RolesAllowed("ADMIN")
public class AdminController {
    @Autowired
    LoggedRepo loggedRepo;
    @Autowired
    AppRoleRepo appRoleRepo;
    @Autowired
    AppUserInterfaceImpl appUserInterface;



    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    private final LigneAbsenceRepo ligneAbsenceRepo;
    private final EnstRepo enstRepo;
    private final SalleRepo salleRepo;
    private final ServerStatusService serverStatusService;
    private final CalculDate calculDate = new CalculDate();
    private AppUserRepo appUserRepo;


    public AdminController(LigneAbsenceRepo ligneAbsenceRepo, EnstRepo enstRepo, SalleRepo salleRepo, ServerStatusService serverStatusService, AppUserRepo appUserRepo ) {
        this.ligneAbsenceRepo = ligneAbsenceRepo;
        this.enstRepo = enstRepo;
        this.salleRepo = salleRepo;
        this.serverStatusService = serverStatusService;
        this.appUserRepo = appUserRepo;




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

        model.addAttribute("user",findLogged());



        return "AbsenceEnseignant"; // Ensure this returns the correct view name
    }
    @GetMapping(path = "/Utilisateurs")
    public String userControl(Model model){
        model.addAttribute("user",findLogged());

        List<AppUser> users=appUserRepo.findAll();
        System.out.println(users);
        model.addAttribute("users",users);
        return "Utilisateurs";
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(dateFormat, true));
    }
    @PostMapping(path = "/SaveEssentielUser")
    public String SaveEssentielUser(Model model,@ModelAttribute("newUser") AppUser appUser){
        String s="Nom d'utilisateur existant";

        System.out.println(appUser);
        if(appUser.getUsername()!=null) {
            AppUser appUser1 = appUserRepo.findByUsername(appUser.getUsername());
            if (appUser1 != null) {
                model.addAttribute("erreur",s);
                return "redirect:/AddUser?us=" + s;

            }}
        appUserRepo.save(appUser);
        System.out.println("Utilisateur enregistré : "+appUser.getUsername());
        return "redirect:/AddUserDetail?us="+appUser.getUsername();




    }

    @GetMapping(path = "/AddUserRole")
    public String AddUserRole(Model model,@RequestParam(value = "err",defaultValue = "") String err ,@RequestParam(value = "us", defaultValue = "") String us) {
        model.addAttribute("user", findLogged());

        String erreur="";
        model.addAttribute("erreur",erreur);


        if (us != null && err.isEmpty()) {
            AppUser appUser = appUserRepo.findByUsername(us);
            model.addAttribute("us", us);
            model.addAttribute("newUser", appUser);


            model.addAttribute("roleCollection1", appUser.getRoleCollection()); // Rôles disponibles


            model.addAttribute("roleCollection", appRoleRepo.findAll()); // Rôles disponibles


        } else if (err!=null|| !err.isEmpty()) {
            AppUser appUser = appUserRepo.findByUsername(us);
            model.addAttribute("us", us);
            model.addAttribute("newUser", appUser);

            erreur="Role déja accordé !";
            model.addAttribute("roleCollection1", appUser.getRoleCollection()); // Rôles disponibles


            model.addAttribute("roleCollection", appRoleRepo.findAll()); // Rôles disponibles

        }

        return "AddUserRole";

    }
    @PostMapping(path = "/AddRoleToUser")
    public String AddRoleToUser(@RequestParam("us") String us,  @RequestParam (value = "role") String role) {
        System.out.println("Role recu " +role);

        if( appUserInterface.AddRoleToUser(us, role)){
            return "redirect:/AddUserRole?us=" + us;}else

        {return "redirect:/AddUserRole?us="+us+"&err=Exist";

        }

    }
    @PostMapping(path = "/deleteRoleToUser")
    public String deleteRoleToUser(@RequestParam("us") String us, @RequestParam("role") String role) {
        System.out.println("Role reçu: " + role);
        System.out.println("User reçu: " + us);

        if (us != null && !us.isEmpty() && role != null && !role.isEmpty()) {
            AppUser appUser = appUserRepo.findByUsername(us);
            System.out.println("Utilisateur trouvé : " + appUser);

            Collection<AppRole> appRoleList = appUser.getRoleCollection();
            System.out.println("Liste des rôles trouvés : " + appRoleList);

            Iterator<AppRole> iterator = appRoleList.iterator();
            while (iterator.hasNext()) {
                AppRole appRole = iterator.next();
                if (appRole.getRoleName().equals(role)) {
                    iterator.remove();
                }
            }

            appUser.setRoleCollection(appRoleList);
            appUserRepo.save(appUser);
            return "redirect:/AddUserRole?us=" + us;
        }

        return "redirect:/AddUserRole?us=" + us + "&err=DeleteFailed";
    }











    @PostMapping(path = "/deleteUser")
    public String deleteUser(@RequestParam(value = "id",defaultValue = "")int id){
        AppUser appUser=appUserRepo.getById(id);

        String imageName=appUser.getPhoto();
        // Create a file object for the image to be deleted
        File file = new File( imageName);

        // Check if the file exists and delete it
        if (file.exists()) {
            file.delete();  // Returns true if the deletion was successful
        } else {
            System.out.println("File not found: " + imageName);

        }appUserRepo.deleteById(id);
        System.out.println("Successs delete : "+id);
        return "redirect:/Utilisateurs";
    }


    @PostMapping(path = "/lockUnlockUser")
    public String lockUnlockUser(@RequestParam(value = "id",defaultValue = "")int id){
        AppUser appUser=appUserRepo.getById(id);
        if(appUser!=null){
            if(appUser.isActif()){
                appUser.setActif(false);
            }else appUser.setActif(true);
        }
        System.out.println("Successs Locking : "+id);
        return "redirect:/Utilisateurs";
    }
    @GetMapping(path = "/EditUser")
    public String EditUser(Model model,@RequestParam(value = "id",defaultValue = "")int id){
        model.addAttribute("user",findLogged());
        AppUser appUser=appUserRepo.getById(id);
        if(appUser!=null){
            model.addAttribute("userEdit",appUser);
            model.addAttribute("roleCollection",appUser.getRoleCollection());
        }

        return "EditUser";
    }

    public AppUser findLogged(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            String utilisateur=((UserDetails)authentication.getPrincipal()).getUsername();
            AppUser appUser1=appUserRepo.findByUsername(utilisateur);
            return appUser1;

        }else return null;
    }

    @GetMapping(path = "/AddUserDetail")
    public String AddUserDetail(Model model,@RequestParam(value = "us", defaultValue = "") String us ){

        model.addAttribute("user",findLogged());
        model.addAttribute("us",us);
        System.out.println("Path recur user details : "+us);
        model.addAttribute("newUser",new AppUser());
        return "AddUserDetail";



    }
    @PostMapping(path = "/saveUserDetail")
    public String SaveUserDetail( @RequestParam(value = "us", defaultValue = "") String us, @ModelAttribute(value = "newUser") AppUser appUser) {
        System.out.println("path recu sur details poste   : " +us);
        if (us != null) {
            AppUser newUser = appUserRepo.findByUsername(us);
            System.out.println("Utilistaeur trouvé : "+newUser);
            if (newUser != null) {
                //newUser.setUsername(newUser.getUsername());

                newUser.setAdresse(appUser.getAdresse()); // Peut être null
                newUser.setAdresse2(appUser.getAdresse2()); // Peut être null
                newUser.setTelephone1(appUser.getTelephone1()); // Peut être null
                newUser.setTelephone2(appUser.getTelephone2()); // Peut être null
                newUser.setTelephone3(appUser.getTelephone3()); // Peut être null
                newUser.setEmail(appUser.getEmail());
                // Vous pouvez également conserver les valeurs non modifiées pour les autres champs requis
                System.out.println("Utilisateur qui va etre enregistré");
                appUserRepo.save(newUser);
                return "redirect:/AddUserRole?us=" + newUser.getUsername();
            } else {
                return "redirect:/AddUserDetail?us=Erreur parvenu !";
            }
        } else {
            return "redirect:/AddUserDetail?us=Erreur parvenu !";
        }
    }


    @GetMapping(path = "/AddUser")
    public String addUser(Model model,@RequestParam(value = "us", defaultValue = "") String us ){
        model.addAttribute("user",findLogged());

        if (us!=""){
            model.addAttribute("erreur",us);

            model.addAttribute("newUser",new AppUser());
            List<AppRole>  list=appRoleRepo.findAll();
            List< AppRole> appRole=new ArrayList<>();
            model.addAttribute("rappRole",appRole);
            model.addAttribute("roleCollection",list);

        }        return "AddUser";

    }
    @PostMapping("/saveUser")
    public String uploadImage(Model model,@ModelAttribute(value = "newUser" )AppUser appUser) {
        if(appUser!=null){
            AppUser test=appUserRepo.findByUsername(appUser.getUsername());
            if(test!=null){
                test.setNom(appUser.getNom());
                test.setPrenom(appUser.getPrenom());
                test.setActif(appUser.isActif());
                test.setEmail(appUser.getEmail());
                test.setTelephone1(appUser.getTelephone1());
                test.setTelephone2(appUser.getTelephone2());
                test.setTelephone3(appUser.getTelephone3());
                test.setAdresse(appUser.getAdresse());
                test.setAdresse2(appUser.getAdresse2());
                test.setRoleCollection(appUser.getRoleCollection());
            }
        }
        return "redirect:/Utilisateurs";}
    @GetMapping(path = "/AddUserPhoto")
    public String AddUserPhoto(Model model ,@RequestParam(value = "us",defaultValue = "")String us){
        model.addAttribute("user",findLogged());
        if(us!=null){
            AppUser appUser=appUserRepo.findByUsername(us);
            if(appUser!=null){
                model.addAttribute("newUser",appUser);
                System.out.println(appUser.getPhoto());
            }
        }

        return "AddUserPhoto";
    }
    @PostMapping(path = "/AddUserImg")
    public String AddUserImg(Model model,@RequestParam String us,@RequestParam("photo") MultipartFile photo) throws IOException {
        System.out.println("Received "+us);
        if (us != null) {
            AppUser appUser = appUserRepo.findByUsername(us);
            if (appUser.getPhoto() != null && !appUser.getPhoto().isEmpty()) {
                System.out.println("ancien path de image :"+appUser.getPhoto());
                File ancienImage = new File("./static/"+appUser.getPhoto());
                if (ancienImage.exists()) {
                    boolean deleted = ancienImage.delete();
                    if (!deleted) {
                        System.out.println("Échec de la suppression de l'ancienne image : " + appUser.getPhoto());
                    } else {
                        System.out.println("Ancienne image supprimée avec succès.");
                    }
                } else {
                    System.out.println("Le fichier n'existe pas : " + appUser.getPhoto());
                }

            }

            if(appUser!=null){

                File uploadDir = new File("./static/");
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String extension = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf("."));
                String errFormat="";
                if(!extension.equals(".jpg")){
                    errFormat="Format .jpg uniquement";
                    model.addAttribute("errorFormat",errFormat);
                    return "redirect:/AddUserPhoto ";

                }
                String newFileName = appUser.getUsername()+ LocalDateTime.now().toString().replace(":","_").replace(".","_")+ extension;
                Path path = Paths.get("./static/" + newFileName);
                System.out.println(path.toString());
                Files.write(path, photo.getBytes());
                appUser.setPhoto("/"+newFileName);
                appUserRepo.save(appUser);
                return "redirect:/AddUserPhoto?us="+appUser.getUsername();
            }
        }
        String success="";
        model.addAttribute("success",success);
        return "redirect:/AddUserPhoto?us="+us;
    }





       /* String CURRENT_USER = "";
        model.addAttribute("user",findLogged());

        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String extension = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf("."));
            String errFormat="";
           if(!extension.equals(".jpg")){
               errFormat="Format .jpg uniquement";
               model.addAttribute("errorFormat",errFormat);
               return "redirect:/Profile ";

           }
            String newFileName = CURRENT_USER+ extension;
            Path path = Paths.get(UPLOAD_DIR + newFileName);
System.out.println(path.toString());
            Files.write(path, photo.getBytes());
            findLogged().setPhoto("/uploads/"+newFileName);
            return "redirect:/Profile ";
        } catch (IOException ex) {
            return "Error: " + ex.getMessage();
        }*/












    @GetMapping("/Profile")
    public String profile(Model model,HttpServletRequest request,HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String utilisateur = "";

        model.addAttribute("user",findLogged());

        return "Profile"; // Your Thymeleaf template name
    }



    @RolesAllowed("ADMIN")

    @GetMapping(path = "/AbsenceEtudiant")
    public String etudiantPage(Model model) {
        model.addAttribute("pathCourant", "/AbsenceEtudiant");
        //String utilisateurCourant = "";
        model.addAttribute("user",findLogged());
        String utilisateur = "";



        return "AbsenceEtudiant"; // Ensure this returns the correct view name
    }
    @RolesAllowed("ADMIN")

    @GetMapping(path = "/Dashboard")
    public String dashboardModel(Model model,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "5") int size,
                                 @RequestParam(value = "keyword", defaultValue = "") String keyword) throws IOException {
        String utilisateur = "";

        model.addAttribute("user",findLogged());

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
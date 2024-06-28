package tn.isetsf.presence.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tn.isetsf.presence.Entity.Emploi;
import tn.isetsf.presence.Entity.Ens;
import tn.isetsf.presence.Entity.LigneAbsence;
import tn.isetsf.presence.Repository.EmploiRepo;
import tn.isetsf.presence.Repository.EnstRepo;
import tn.isetsf.presence.Repository.LigneAbsenceRepo;
import tn.isetsf.presence.serviceMail.EmailController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class EmploiController {
    @Autowired
    EnstRepo enstRepo;
    @Autowired
    LigneAbsenceRepo ligneAbsenceRepo;
    @Autowired
    EmploiRepo emploiRepo;
    @Autowired
    private  EmailController emailController;

    @GetMapping(value = "/emploi/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Emploi> getAllEmploi(){
        return (ArrayList<Emploi>) emploiRepo.findAll();
    }

    @PostMapping(value = "/ens",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Ens> getEnseignant(){
         return enstRepo.findAll();

           }




    @GetMapping(value = "/emploi/creneau")
    public Boolean getEnsi(@RequestParam String jour,@RequestParam String salle,@RequestParam String seance,@RequestParam String seanceDouble){
        List<Emploi> emploi= emploiRepo.trouverCreneau(jour, salle, seance,seanceDouble);
        LigneAbsence ligneAbsence=new LigneAbsence();
        Boolean notified=false;
      Optional<Ens> ens=  enstRepo.findById(emploi.get(0).getEnsi1());
      String msg="Mr "+ens.get().getNomEnseignant() + "On vous informe que vous etes  absent le : " +LocalDate.now()+" à la salle : " +salle+" seances de :" + emploi.get(0).getNom_seance();

       if(!emploi.isEmpty()){
            for(Emploi emploi1:emploi){
                ligneAbsence.setEnsi1(emploi1.getEnsi1());
                ligneAbsence.setNom_jour(emploi1.getNom_jour());
                ligneAbsence.setAnnee1(emploi1.getAnnee1());
                ligneAbsence.setSemestre1(emploi1.getSemestre1());
                ligneAbsence.setNom_salle(emploi1.getNom_salle());
                 ligneAbsence.setNom_matiere(emploi1.getNom_matiere());
                 ligneAbsence.setNom_seance(emploi1.getNom_seance());
                 ligneAbsence.setSeanceDouble(emploi1.getSeance1());
                 ligneAbsence.setDate(LocalDate.now());

            }
           try {

               ligneAbsence.setNotified( emailController.sendEmail(ens.get().getEmail_enseignant(), "Service Scolarité ISET SFAX : Notification d'absence", msg));
ligneAbsence.setNotified(true);
           }catch (Exception ignored){
               ligneAbsence.setNotified(false);
           }
           ligneAbsenceRepo.save(ligneAbsence);
           System.out.println("ligne absence ajouté"+ligneAbsence.toString());

             return true;
         }
        else {
            return false;
       }
    }
    @PutMapping(value = ("/ens/update"),consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean updateUser(@RequestParam Long mat, @RequestBody Ens ens){
        Optional<Ens> ens1 =enstRepo.findById(String.valueOf(mat));
        if(ens1.isPresent()){
            ens1.get().setEmail_enseignant(ens.getEmail_enseignant());
            ens1.get().setTel_enseignant(ens.getTel_enseignant());
            enstRepo.save(ens1.get());
            return true;
        }else {
            return false;
        }
    }

}

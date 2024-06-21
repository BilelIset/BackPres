package tn.isetsf.presence.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.isetsf.presence.Entity.Emploi;
import tn.isetsf.presence.Entity.Ens;
import tn.isetsf.presence.Entity.LigneAbsence;
import tn.isetsf.presence.Repository.EmploiRepo;
import tn.isetsf.presence.Repository.EnstRepo;
import tn.isetsf.presence.Repository.LigneAbsenceRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class EmploiController {
    @Autowired
    EnstRepo enstRepo;
    @Autowired
    LigneAbsenceRepo ligneAbsenceRepo;
    @Autowired
    EmploiRepo emploiRepo;

    @GetMapping(value = "/emploi/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Emploi> getAllEmploi(){
        return (ArrayList<Emploi>) emploiRepo.findAll();
    }

    @GetMapping(value = "/ens",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Ens> getEnseignant(){
         return enstRepo.findAll();

           }
    @GetMapping(value = "/emploi/creneau")
    public Boolean getEnsi(@RequestParam String jour,@RequestParam String salle,@RequestParam String seance,@RequestParam String seanceDouble){
        List<Emploi> emploi= emploiRepo.trouverCreneau(jour, salle, seance,seanceDouble);
        LigneAbsence ligneAbsence=new LigneAbsence();
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
            System.out.println("ligne absence ajouté"+ligneAbsence.toString());
            ligneAbsenceRepo.save(ligneAbsence);
             return true;
         }
        else {
            return false;
        }
    }

}

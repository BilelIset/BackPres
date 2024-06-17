/*package tn.isetsf.presence.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.isetsf.presence.CalculDate;
import tn.isetsf.presence.Entity.Emploi;
import tn.isetsf.presence.Entity.EmploiDuJour;
import tn.isetsf.presence.Entity.Resp;
import tn.isetsf.presence.Repository.EmploiJourRepo;
import tn.isetsf.presence.Repository.EmploiRepo;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")

public class EmploiDuJourController {
    @Autowired
    EmploiRepo emploiRepo;
    @Autowired
    EmploiJourRepo emploiDuJourRepo;
    CalculDate calculDate=new CalculDate();
    int calendar=Calendar.YEAR;
    Boolean charged=false;

    @GetMapping(value = "/getempljour",produces = MediaType.APPLICATION_JSON_VALUE)
    @Scheduled(fixedDelay = 86400000)
    public Resp chargerEmploiJours(){
        Resp resp = new Resp();
        resp.setStatue(false);
        resp.setAdmin(false);

        if(emploiDuJourRepo.verifChargemntEmploi(calculDate.getDate()).isEmpty()){
    System.out.println(calculDate.getYear()+" "+calculDate.getSemestre()+" "+ calculDate.indexJour());
    List<Emploi> emploisExternes = emploiRepo.getEmloiDeJour(String.valueOf(calculDate.getYear()), String.valueOf(calculDate.getSemestre()), String.valueOf(calculDate.indexJour()));
    List<EmploiDuJour> emploisDuJour = new ArrayList<>();

try {


    for (Emploi emploiExterne : emploisExternes) {
        EmploiDuJour emploiDuJour = new EmploiDuJour();
        emploiDuJour.setAnnees(emploiExterne.getAnnee1());
        emploiDuJour.setSemestre(emploiExterne.getSemestre1());
        emploiDuJour.setJour(emploiExterne.getJour1());
        emploiDuJour.setSeance(emploiExterne.getSeance1());
        emploiDuJour.setSalle(emploiExterne.getSalle1());
        emploiDuJour.setEnseignant(emploiExterne.getEnsi1());
        emploiDuJour.setNiveaux(emploiExterne.getNom_niveau());
        emploiDuJour.setMatiere(emploiExterne.getNom_matiere());
        emploiDuJour.setPar15(emploiExterne.getPar15());
        emploiDuJour.setNomSalle(emploiExterne.getNom_salle());
        emploiDuJour.setDate(calculDate.getDate());

        emploisDuJour.add(emploiDuJour);
    }
    emploiDuJourRepo.saveAll(emploisDuJour);
    resp.setAdmin(true);
    resp.setStatue(true);
    return resp;

}catch (Exception e){
    System.out.println();
}
    }return resp;
    }



}
*/
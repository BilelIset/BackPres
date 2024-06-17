/*package tn.isetsf.presence.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.isetsf.presence.CalculDate;
import tn.isetsf.presence.Repository.EmploiJourRepo;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")

public class PointageController {
    @Autowired
    EmploiJourRepo emploiJourRepo;
    CalculDate calculDate=new CalculDate();
    @GetMapping(value = "/creneau",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pointage> chargerVrenaux(){
        List<EmploiDuJour> list=new ArrayList<>();
        list=  emploiJourRepo.chargerSeanceEnCour(calculDate.getDate(), String.valueOf(calculDate.getSeance()),String.valueOf(calculDate.getSeanceDouble()));
        List<Pointage> pointageList=new ArrayList<>();
        for(EmploiDuJour  emploiDuJour:list){
            Pointage pointage=new Pointage();
            pointage.setPresent(true);
            pointage.setSeance(Integer.parseInt(emploiDuJour.getSeance()));
            pointage.setSalle(Integer.parseInt(emploiDuJour.getSalle()));
            pointage.setDate(calculDate.getDate());
        }
        return pointageList;
    }
}
*/
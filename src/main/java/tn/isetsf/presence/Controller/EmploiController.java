package tn.isetsf.presence.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.isetsf.presence.Entity.Emploi;
import tn.isetsf.presence.Repository.EmploiRepo;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmploiController {
    @Autowired
    EmploiRepo emploiRepo;
    @GetMapping(value = "/emploi/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Emploi> getAllEmploi(){
        return (ArrayList<Emploi>) emploiRepo.findAll();
    }
    @GetMapping(value = "/emploi/ensi")
    public List<Emploi> getEnsi(@RequestParam String jour1,@RequestParam String salle1,@RequestParam String seance1){
        return  emploiRepo.trouverEnsi(jour1,salle1,seance1);
    }

}

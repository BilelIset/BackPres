package tn.isetsf.presence.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.isetsf.presence.CalculDate;
import tn.isetsf.presence.Entity.Emploi;
import tn.isetsf.presence.Entity.LigneAbsence;
import tn.isetsf.presence.Entity.LigneEmploi;
import tn.isetsf.presence.Repository.EmploiRepo;
import tn.isetsf.presence.Repository.LigneAbsenceRepo;
import tn.isetsf.presence.Repository.LigneEmploiRepo;

import java.util.*;

@RestController

public class LigneEmploiController {
    @Autowired
    LigneAbsenceRepo absenceRepoRepo;
    @GetMapping(value = "/absence",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LigneAbsence> getAbscece(){
        return absenceRepoRepo.findAll();
    }

}

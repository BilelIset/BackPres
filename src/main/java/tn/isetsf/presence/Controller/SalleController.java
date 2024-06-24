package tn.isetsf.presence.Controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tn.isetsf.presence.CalculDate;
import tn.isetsf.presence.Entity.Emploi;
import tn.isetsf.presence.Entity.Salle;
import tn.isetsf.presence.Repository.EmploiRepo;
import tn.isetsf.presence.Repository.SalleRepo;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class SalleController {
    @Autowired
    SalleRepo salleRepo;
    @Autowired
    EmploiRepo emploiRepo;
    CalculDate calculDate=new CalculDate();

    @PostMapping(value = "/salle",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Salle> getSalle(){

        return salleRepo.findAll();

    }
    @GetMapping(value = "/dep",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Salle> getSalleParDep(@RequestParam String nomdep) {
        return salleRepo.getdept(nomdep);
    }
    @GetMapping(value = "/dep/nbre",produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getNbreSalleParDEp(@RequestParam String nomdep) {
        return salleRepo.getNbreSalleParDep(nomdep);
    }

}

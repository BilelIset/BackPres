package tn.isetsf.presence.serviceMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.isetsf.presence.Repository.EnstRepo;

import java.time.LocalDate;

@RestController
@Service
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    EnstRepo ens;

    public Boolean sendEmail( String to,  String subject,  String text) {
try {
    emailService.sendSimpleEmail(to, subject, text);
    System.out.println("succes d'envoie à : " + to);

    return true;
}catch (Exception e){
    System.out.println("Echec d'envoie à  " + to);

    return false;

}
    }
    @PostMapping(value = "/sendemail")
    public Boolean sendEmail2( @RequestParam String to,  @RequestParam String subject, @RequestParam String text) {

        try {
            emailService.sendSimpleEmail(to, subject, text);
            System.out.println("succes d'envoie à : " + to);

            return true;
        }catch (Exception e){
            System.out.println("Echec d'envoie à  " + to);

            return false;

        }
    }
}

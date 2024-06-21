package tn.isetsf.presence.serviceMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/envoi")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        String sujet = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email Template</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>"+subject+"</h1>\n" +
                "    <p >"+text+"</p>\n" +
                "    <img src=\"https://img.freepik.com/photos-gratuite/contexte-energie-nucleaire-ia-innovation-future-technologie-rupture_53876-129783.jpg?size=626&ext=jpg&ga=GA1.2.592579683.1704110822&semt=sph\" alt=\"Image intégrée\">\n" +
                "    <p>Fin de l'e-mail.</p>\n" +
                "</body>\n" +
                "</html>\n";        emailService.sendSimpleEmail(to, subject, sujet);
        return "Email sent successfully!";
    }
}

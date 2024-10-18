package tn.isetsf.presence.webThymeleaf;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class ServerStatusService {

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean checkMobileAppServerStatus(String url) {


        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            // Return true only if the response status is 200 OK
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Catch any HTTP error status (like 404, 500, etc.) and return false
            return false;
        } catch (Exception e) {
            // Catch any other exceptions (e.g., timeouts, network issues) and return false
            return false;

        }}
    public boolean checkMailServer(String url) {
        try {
            // Envoyer la requête à l'URL Actuator
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Vérifier si la réponse contient "DOWN"
            boolean isDown = Objects.requireNonNull(response.getBody()).contains("DOWN");

            // Afficher le statut dans la console pour diagnostic
            System.out.println("Status Actuator contient 'DOWN' : " + isDown);

            // Retourner false si "DOWN", sinon true
            return isDown;

        } catch (Exception e) {
            // Gérer les exceptions (par ex: URL incorrecte, serveur inaccessible, etc.)
            System.err.println("Erreur lors de la vérification du serveur de messagerie : " + e.getMessage());
            return false; // Considérer que le serveur est hors service si une exception survient
        }
    }}

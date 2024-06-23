package tn.isetsf.presence.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

public class BasicAuthenticationFilter extends OncePerRequestFilter {

    private final String password = "motdepasse"; // Remplacez par votre mot de passe

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String encodedCredentials = authHeader.substring(6); // Supprimer le préfixe "Basic "
            String credentials = new String(Base64.getDecoder().decode(encodedCredentials));
            String[] parts = credentials.split(":");
            if (parts.length == 2 && parts[1].equals(password)) {
                filterChain.doFilter(request, response); // Autoriser l'accès
                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Accès non autorisé
        response.setHeader("WWW-Authenticate", "Basic realm=\"Protected Area\"");
    }
}


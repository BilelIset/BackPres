package tn.isetsf.presence;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Autoriser toutes les origines
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Autoriser certaines méthodes HTTP
                .allowedHeaders("*"); // Autoriser tous les en-têtes
    }
}

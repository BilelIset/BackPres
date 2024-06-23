package tn.isetsf.presence.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends SecurityConfigurerAdapter {


    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll() // Autoriser toutes les requêtes
                .and()
                .addFilterBefore(new BasicAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Enregistrer le filtre avant l'authentification par nom d'utilisateur et mot de passe
    }
}

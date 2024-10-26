package tn.isetsf.presence.sec.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import tn.isetsf.presence.sec.entity.AppRole;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String username;

    private String nom;

    private String prenom;

    private String adresse="";
    private String adresse2="";
    private String email="";

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateNaissance;

    private Integer telephone1=0;
    private Integer telephone2=0;
    private Integer telephone3=0;
    private boolean actif=true;

    private String photo="";

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roleCollection = new ArrayList<>();
}

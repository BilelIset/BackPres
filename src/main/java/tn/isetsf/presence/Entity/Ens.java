package tn.isetsf.presence.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ens {
    @Id
    private String matEnseignant;

    private String nomEnseignant;
    private String depEnseignant;


    private String email_enseignant;
    private String tel_enseignant;

}

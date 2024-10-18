package tn.isetsf.presence.Entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;


import javax.persistence.Entity;
import javax.persistence.Id;

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

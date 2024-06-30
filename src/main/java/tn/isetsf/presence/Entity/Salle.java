package tn.isetsf.presence.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor@NoArgsConstructor
public class Salle {
    @Id
    private int salle1;
    private String nom_salle;
    private  String nomdepsalle;
    private String seanceDouble;
    private String nomSeance;
}

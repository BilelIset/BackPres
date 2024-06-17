package tn.isetsf.presence.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LigneAbsence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAbsence;

    private Date dateJour;
    private String observation;
    private LocalDateTime dateHeureEnregistrement;

    @ManyToOne
    @JoinColumn(name = "salle_id")
    private Salle salle1;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant ensi1;



}

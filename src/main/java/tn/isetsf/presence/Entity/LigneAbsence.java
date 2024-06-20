package tn.isetsf.presence.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LigneAbsence {
@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ligne_absence_seq")
    @SequenceGenerator(name = "ligne_absence_seq", sequenceName = "ligne_absence_seq", allocationSize = 1)
    private Long num;
    private String nom_salle;
    private String nom_matiere;
    private String ensi1;
    private String nom_seance;
    private String seanceDouble;
    private String annee1;
    private String semestre1;
    private String nom_jour;
    private LocalDate date;

}



package tn.isetsf.presence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.isetsf.presence.Entity.LigneAbsence;

import java.time.LocalDate;
import java.util.List;

public interface LigneAbsenceRepo extends JpaRepository<LigneAbsence,Long> {
    @Query("SELECT l FROM LigneAbsence l WHERE l.nom_salle= :salle AND  l.seanceDouble= :seance AND l.ensi1= :enseignant")
    List<LigneAbsence> trouverAbsence(  String salle,String seance,String enseignant);
}

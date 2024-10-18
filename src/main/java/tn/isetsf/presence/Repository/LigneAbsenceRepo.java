package tn.isetsf.presence.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.isetsf.presence.Entity.LigneAbsence;

import java.time.LocalDate;
import java.util.List;

public interface LigneAbsenceRepo extends JpaRepository<LigneAbsence,Long> {
    @Query("SELECT l FROM LigneAbsence l WHERE l.date=:date AND l.nom_salle= :salle AND  l.seanceDouble= :seance AND l.ensi1= :enseignant")
    List<LigneAbsence> trouverAbsence( LocalDate date, String salle,String seance,String enseignant);
    Page<LigneAbsence> findByEnsi1Contains(String keyword, Pageable pageable);
    @Query(value = "SELECT ensi1, COUNT(*) AS nombre_absences FROM ligne_absence GROUP BY ensi1 ORDER BY nombre_absences DESC;", nativeQuery = true)
    List<Object[]> countAbsencesByEnseignantNative();

    List<LigneAbsence> findByNotifiedFalse();


}

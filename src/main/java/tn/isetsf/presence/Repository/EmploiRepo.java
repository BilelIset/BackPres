package tn.isetsf.presence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.isetsf.presence.Entity.Emploi;

import java.util.List;

public interface EmploiRepo extends JpaRepository<Emploi,String> {
    @Query("SELECT e FROM Emploi e WHERE e.jour1 = :jrs AND e.nom_salle = :salle1 AND (e.seance1= :seance OR e.seance1= :seance2)")
    List<Emploi> trouverEnsi(String jrs,String salle1,String seance,String seance2);



}


package tn.isetsf.presence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.isetsf.presence.EmploiProjection;
import tn.isetsf.presence.Entity.Emploi;

import java.util.ArrayList;
import java.util.List;

public interface EmploiRepo extends JpaRepository<Emploi,String> {
    @Query("SELECT e FROM Emploi e WHERE e.jour1 = :jrs AND e.nom_salle = :salle1 AND (e.seance1= :seance OR e.seance1= :seance2)")
    List<Emploi> trouverCreneau(String jrs, String salle1, String seance, String seance2);
    @Query("SELECT e.nom_salle AS nom_salle, e.salle1 AS salle1,e.nomdepsalle AS nomdepsalle FROM Emploi e WHERE e.jour1 = :jrs AND (e.seance1 = :seance OR e.seance1 = :seance2)")
    List<EmploiProjection> trouverCren(String jrs, String seance, String seance2);


    @Query("SELECT e.ensi1,e.nom_ensi,e.nomdepEnseignant FROM Emploi e")
    ArrayList<Object> trouverEns();


}


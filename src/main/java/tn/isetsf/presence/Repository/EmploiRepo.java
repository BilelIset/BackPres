package tn.isetsf.presence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.isetsf.presence.Entity.Emploi;

import java.util.List;

public interface EmploiRepo extends JpaRepository<Emploi,String> {
    @Query("SELECT e FROM Emploi e WHERE e.jour1 = :jrs AND e.salle1= :salle AND e.seance1= :seance")
    List<Emploi> trouverEnsi(String jrs,String salle,String seance);



}


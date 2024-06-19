package tn.isetsf.presence.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isetsf.presence.Entity.LigneAbsence;

public interface LigneAbsenceRepo extends JpaRepository<LigneAbsence,String> {
}

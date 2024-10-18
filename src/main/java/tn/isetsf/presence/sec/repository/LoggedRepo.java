package tn.isetsf.presence.sec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isetsf.presence.sec.entity.Logged;

public interface LoggedRepo extends JpaRepository<Logged,Integer> {
    Logged getByLogName( String log);
}

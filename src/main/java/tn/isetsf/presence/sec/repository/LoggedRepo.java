package tn.isetsf.presence.sec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isetsf.presence.sec.entity.Logged;

import java.util.List;

public interface LoggedRepo extends JpaRepository<Logged,Integer> {
    Logged getByLogName( String log);
    Logged findByLogNameAndSessionId(String log,String session);
   List< Logged> findByConnectedTrue();
}

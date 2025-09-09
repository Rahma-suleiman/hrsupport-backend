package hm.project.hrsupport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hm.project.hrsupport.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>{


    Optional<Application> findByFirstNameAndLastNameAndEmail(String firstName, String lastName, String email);
    
}

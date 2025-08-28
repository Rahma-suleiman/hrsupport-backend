package hm.project.hrsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hm.project.hrsupport.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>{
    
}

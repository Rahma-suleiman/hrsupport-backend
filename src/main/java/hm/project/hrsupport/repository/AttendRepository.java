package hm.project.hrsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hm.project.hrsupport.entity.Attendance;
@Repository
public interface AttendRepository extends JpaRepository<Attendance, Long>{
    
}

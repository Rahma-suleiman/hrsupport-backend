package hm.project.hrsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hm.project.hrsupport.entity.LeaveRequest;
@Repository
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long>{
    
}

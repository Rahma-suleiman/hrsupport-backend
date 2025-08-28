package hm.project.hrsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hm.project.hrsupport.entity.Department;

@Repository
public interface DeptRepository extends JpaRepository<Department, Long>{
    
}

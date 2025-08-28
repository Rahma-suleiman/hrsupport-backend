package hm.project.hrsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hm.project.hrsupport.entity.Employee;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Long>{
    
}

package hm.project.hrsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hm.project.hrsupport.entity.Payroll;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long>{
    
}

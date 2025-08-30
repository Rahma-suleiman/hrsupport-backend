package hm.project.hrsupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hm.project.hrsupport.entity.JobPosting;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long>{
    
}

package hm.project.hrsupport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hm.project.hrsupport.entity.PerformanceReview;

@Repository
public interface PerformReviewRepository extends JpaRepository<PerformanceReview, Long>{

    // List<PerformanceReview> findByReveiwerId(Long id);

    List<PerformanceReview> findByReviewerId(Long id);
    
}

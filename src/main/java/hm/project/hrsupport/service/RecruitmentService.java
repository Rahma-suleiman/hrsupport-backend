package hm.project.hrsupport.service;

import org.springframework.stereotype.Service;

import hm.project.hrsupport.repository.RecruitmentRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    
}

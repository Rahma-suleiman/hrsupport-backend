package hm.project.hrsupport.service;

import org.springframework.stereotype.Service;

import hm.project.hrsupport.repository.ApplicationRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    
}

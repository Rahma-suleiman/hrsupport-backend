package hm.project.hrsupport.service;

import org.springframework.stereotype.Service;

import hm.project.hrsupport.repository.LeaveRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LeaveService {
    private final LeaveRepository leaveRepository;
}

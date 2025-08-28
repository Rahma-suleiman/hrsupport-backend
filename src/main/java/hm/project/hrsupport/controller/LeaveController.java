package hm.project.hrsupport.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.project.hrsupport.service.LeaveService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/hrsupport/leave")
public class LeaveController {
    private final LeaveService leaveService;
    
}

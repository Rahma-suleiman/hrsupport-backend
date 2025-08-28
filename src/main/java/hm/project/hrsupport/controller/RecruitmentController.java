package hm.project.hrsupport.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.project.hrsupport.service.RecruitmentService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/hrsupport/recruitment")
public class RecruitmentController {
    
    private final RecruitmentService recruitmentService;
}

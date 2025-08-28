package hm.project.hrsupport.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.project.hrsupport.service.ApplicationService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/hrsupport/application")
public class ApplicationController {
    
    private final ApplicationService applicationService;
}

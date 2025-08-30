package hm.project.hrsupport.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.project.hrsupport.dto.ApplicationDTO;
import hm.project.hrsupport.service.ApplicationService;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/hrsupport/application")
public class ApplicationController {
    
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationDTO> addApplication(@RequestBody ApplicationDTO applicationDTO) {
        ApplicationDTO application = applicationService.addApplication(applicationDTO);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long id) {
        ApplicationDTO appDto = applicationService.getApplicationById(id);
        return new ResponseEntity<>(appDto,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        List<ApplicationDTO> allApps = applicationService.getAllApplications();
        return ResponseEntity.ok(allApps);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDTO> editApplication(@PathVariable Long id, @RequestBody ApplicationDTO applicationDTO) {
        ApplicationDTO updateApp = applicationService.editApplication(id, applicationDTO);
        return ResponseEntity.ok(updateApp);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
    


}

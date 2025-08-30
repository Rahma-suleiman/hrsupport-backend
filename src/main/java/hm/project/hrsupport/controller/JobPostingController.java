package hm.project.hrsupport.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.project.hrsupport.dto.JobPostingDTO;
import hm.project.hrsupport.service.JobPostingService;
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



@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/hrsupport/jobposting")
public class JobPostingController {
    private final JobPostingService jobPostingService;

    @PostMapping
    public ResponseEntity<JobPostingDTO> postJob(@RequestBody JobPostingDTO jobPostingDTO) {
        JobPostingDTO jobDto = jobPostingService.postJob(jobPostingDTO);
        return new ResponseEntity<>(jobDto, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<JobPostingDTO>> getAlJobPost() {
        List<JobPostingDTO> jobPost = jobPostingService.getAllJobPost();
        return ResponseEntity.ok(jobPost);
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobPostingDTO> getJobPostById(@PathVariable Long id) {
        JobPostingDTO jobPost= jobPostingService.getJobPostById(id);
        return new ResponseEntity<>(jobPost, HttpStatus.OK);
    }
       @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPosting(@PathVariable Long id) {
        jobPostingService.deleteJobPosting(id);
        return ResponseEntity.noContent().build();
    }
    
}

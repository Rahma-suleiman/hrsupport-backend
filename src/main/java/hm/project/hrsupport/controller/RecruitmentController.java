package hm.project.hrsupport.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.project.hrsupport.dto.RecruitmentDTO;
import hm.project.hrsupport.service.RecruitmentService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v2/hrsupport/recruitment")
public class RecruitmentController {
    
    private final RecruitmentService recruitmentService;

    @PostMapping
    public ResponseEntity<RecruitmentDTO> addRecruitment(@RequestBody RecruitmentDTO recruitmentDTO) {
        RecruitmentDTO recruit = recruitmentService.addRecruitment(recruitmentDTO);
        return new ResponseEntity<>(recruit, HttpStatus.CREATED);
    }
    


}

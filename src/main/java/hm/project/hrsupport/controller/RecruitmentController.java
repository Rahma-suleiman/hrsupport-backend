package hm.project.hrsupport.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.project.hrsupport.dto.RecruitmentDTO;
import hm.project.hrsupport.service.RecruitmentService;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




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
    @GetMapping
    public ResponseEntity<List<RecruitmentDTO>> getAllRecruits() {
        List<RecruitmentDTO> recruitDto = recruitmentService.getAllRecruits();
        return ResponseEntity.ok(recruitDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RecruitmentDTO> getRecruitById(@PathVariable Long id) {
        RecruitmentDTO recruit = recruitmentService.getRecruitById(id);
        return ResponseEntity.ok(recruit);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RecruitmentDTO> editRecruitment(@PathVariable Long id, @RequestBody RecruitmentDTO recruitmentDTO) {
        RecruitmentDTO updatedRecruit = recruitmentService.editRecruitment(id, recruitmentDTO);
        return new ResponseEntity<>(updatedRecruit, HttpStatus.OK);
    }
     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecruitment(@PathVariable Long id) {
        recruitmentService.deleteRecruitment(id);
        return ResponseEntity.noContent().build();
    }
    


}

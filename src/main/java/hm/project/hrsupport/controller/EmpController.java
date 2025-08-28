package hm.project.hrsupport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.project.hrsupport.dto.EmpDTO;
import hm.project.hrsupport.dto.PerformReviewDTO;
import hm.project.hrsupport.service.EmpService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/api/v2/hrsupport/employee")
// http://localhost:8080/api/v2/hrsupport/employee
public class EmpController {
    
    @Autowired
    private EmpService empService;
    
    @GetMapping
    public ResponseEntity<List<EmpDTO>> getAllEmployee() {
        List<EmpDTO> emp = empService.getAllEmployee();
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(empService.getEmployeeById(id));
    }
    @PostMapping
    public ResponseEntity<EmpDTO> addEmployee(@RequestBody EmpDTO empDTO) {
        EmpDTO emp = empService.createEmployee(empDTO);
        return new ResponseEntity<>(emp, HttpStatus.CREATED);
    }
    @PutMapping("path/{id}")
    public ResponseEntity<EmpDTO> editEmployee(@PathVariable Long id, @RequestBody EmpDTO empDTO) {
        EmpDTO emp = empService.editEmployee(id, empDTO);
        return new ResponseEntity<>(emp,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id){
        empService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }
      // Employee sees their own reviews
    @GetMapping("/{id}/reviews/received")
    public ResponseEntity<List<PerformReviewDTO>> getMyReviews(@PathVariable Long id) {
        List<PerformReviewDTO> myReview = empService.getMyReviews(id);
        return ResponseEntity.ok(myReview);
    }     
    // Manager sees reviews they wrote
    @GetMapping("/{id}/reviews/written")
    public ResponseEntity<List<PerformReviewDTO>> getReviewsWrittenByMe(@PathVariable Long id) {
        List<PerformReviewDTO> myReview = empService.getReviewsWrittenByMe(id);
        return ResponseEntity.ok(myReview);
    }
    
    
}


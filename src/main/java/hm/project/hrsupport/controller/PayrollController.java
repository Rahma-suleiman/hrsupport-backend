package hm.project.hrsupport.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hm.project.hrsupport.dto.PayrollDTO;
import hm.project.hrsupport.service.PayrollService;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@AllArgsConstructor // with this no need to inject constructor
@RequestMapping("/api/v2/hrsupport/payroll")
public class PayrollController {

    private final PayrollService payrollService;
    @GetMapping
    public ResponseEntity<List<PayrollDTO>> getAllPayroll() {
        List<PayrollDTO> payroll = payrollService.getAllPayroll();
        return ResponseEntity.ok(payroll);
    }
    @PostMapping
    public ResponseEntity<PayrollDTO> addPayroll(@RequestBody PayrollDTO payrollDTO) {
        PayrollDTO payroll = payrollService.addPayroll(payrollDTO);
        return new ResponseEntity<>(payroll, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayroll(@PathVariable Long id) {
        payrollService.deletePayroll(id);
        return ResponseEntity.noContent().build();
    }
    
    
}

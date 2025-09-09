package hm.project.hrsupport.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.PayrollDTO;
import hm.project.hrsupport.entity.Employee;
import hm.project.hrsupport.entity.Payroll;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.EmpRepository;
import hm.project.hrsupport.repository.PayrollRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final ModelMapper modelMapper;
    private final EmpRepository empRepository;

    public List<PayrollDTO> getAllPayroll() {
        List<Payroll> payroll = payrollRepository.findAll();
        return payroll.stream()
                .map(pay -> {
                    PayrollDTO payDto = modelMapper.map(pay, PayrollDTO.class);
                    // If your PayrollDTO has employeeId but your Payroll entity has an Employee
                    // object, ModelMapper won’t automatically map employee.id(i.e obj) to employeeId.
                    payDto.setEmployeeId(pay.getEmployee().getId());
                    return payDto;
                })

                .collect(Collectors.toList());
    }

    public PayrollDTO addPayroll(PayrollDTO payrollDTO) {

        Payroll payroll = modelMapper.map(payrollDTO, Payroll.class);
        Employee employee = empRepository.findById(payrollDTO.getEmployeeId())
                .orElseThrow(() -> new ApiRequestException("Cannot get payroll for employee id " + payrollDTO.getEmployeeId()));

        payroll.setEmployee(employee);

        payroll.setSalary(employee.getSalary());
        // Net Salary=Salary+Bonus−Deductions
        int netSalary = employee.getSalary()
                + (payrollDTO.getBonus() != null ? payrollDTO.getBonus() : 0)
                - (payrollDTO.getDeduction() != null ? payrollDTO.getDeduction() : 0);

        payroll.setNetSalary(netSalary);

        Payroll savedPayroll = payrollRepository.save(payroll);

        PayrollDTO payrollDtoResponse = modelMapper.map(savedPayroll, PayrollDTO.class);

        // Ensure read-only fields are returned
        payrollDtoResponse.setSalary(savedPayroll.getSalary());
        payrollDtoResponse.setNetSalary(savedPayroll.getNetSalary());
        return payrollDtoResponse;
    }

    public void deletePayroll(Long id) {
        payrollRepository.deleteById(id);
    }

}
// {
// "year": 2025,
// "month": 8,
// "bonus": 500,
// "deduction": 0,
// "paymentStatus": "PAID",
// "employeeId": 1
// }
// {
// "year": 2025,
// "month": 7,
// "bonus": 0,
// "deduction": 200,
// "paymentStatus": "UNPAID",
// "employeeId": 2
// }

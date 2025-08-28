package hm.project.hrsupport.service;

import java.util.stream.Collectors;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.EmpDTO;
import hm.project.hrsupport.dto.PerformReviewDTO;
import hm.project.hrsupport.entity.Department;
import hm.project.hrsupport.entity.Employee;
import hm.project.hrsupport.entity.PerformanceReview;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.DeptRepository;
import hm.project.hrsupport.repository.EmpRepository;
import hm.project.hrsupport.repository.PerformReviewRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmpService {

        private final ModelMapper modelMapper;
        private final EmpRepository empRepository;
        private final DeptRepository deptRepository;
        private final PerformReviewRepository performReviewRepository;



        public List<EmpDTO> getAllEmployee() {
                List<Employee> employees = empRepository.findAll();
                return employees.stream()
                                .map(emp -> {
                                        EmpDTO empDto = modelMapper.map(emp, EmpDTO.class);

                                        empDto.setManagerId(emp.getManager() != null ? emp.getManager().getId() : null);
                                        empDto.setSubordinateIds(emp.getSubordinates() != null
                                                        ? emp.getSubordinates().stream()
                                                                        .map(sub -> sub.getId()) // just ids instead of full mapping
                                                                        .collect(Collectors.toList())
                                                        : null);
                                        empDto.setDepartmentId(emp.getDepartment().getId());
                                        return empDto;
                                }).collect(Collectors.toList());
        }


        public EmpDTO getEmployeeById(Long id) {
                Employee emp = empRepository.findById(id)
                                .orElseThrow(() -> new IllegalStateException("emploee not found with id" + id));
                EmpDTO empDtoResponse = modelMapper.map(emp, EmpDTO.class);

                empDtoResponse.setManagerId(emp.getManager() != null ? emp.getManager().getId() : null);

                empDtoResponse.setSubordinateIds(emp.getSubordinates() != null
                                ? emp.getSubordinates().stream()
                                                .map(sub -> sub.getId())
                                                .collect(Collectors.toList())
                                : null);
                return empDtoResponse;
        }

        public EmpDTO createEmployee(EmpDTO empDTO) {
                Employee employee = modelMapper.map(empDTO, Employee.class);

                Employee manager = null;
                // set Manager(optional)
                if (empDTO.getManagerId() != null) {
                        manager = empRepository.findById(empDTO.getManagerId())
                                        .orElseThrow(() -> new ApiRequestException(
                                                        "Manager not found with id" + empDTO.getManagerId()));
                        employee.setManager(manager);

                        manager.getSubordinates().add(employee);
                        empRepository.save(manager); 
                }
                if (empDTO.getDepartmentId() == null) {
                        throw new ApiRequestException("Department is required for every employee");
                }

                Department department = deptRepository.findById(empDTO.getDepartmentId())
                                .orElseThrow(() -> new ApiRequestException("Department not found"));
                employee.setDepartment(department);

                Employee savedEmployee = empRepository.save(employee);

                EmpDTO empResponse = modelMapper.map(savedEmployee, EmpDTO.class);

                empResponse.setManagerId(
                                savedEmployee.getManager() != null ? savedEmployee.getManager().getId() : null);

                empResponse.setSubordinateIds(savedEmployee.getSubordinates() != null
                                ? savedEmployee.getSubordinates().stream()
                                                .map(sub -> modelMapper.map(sub, EmpDTO.class).getId())
                                                .collect(Collectors.toList())
                                : null);

                empResponse.setDepartmentId(savedEmployee.getDepartment() != null
                                                ? savedEmployee.getDepartment().getId()
                                                : null);
                return empResponse;
        }

        public void deleteEmployeeById(Long id) {
                empRepository.deleteById(id);
        }

        public EmpDTO editEmployee(Long id, EmpDTO empDTO) {
                Employee existingEmp = empRepository.findById(id)
                                .orElseThrow(() -> new ApiRequestException("Employee not found with ID:" + id));
                empDTO.setId(existingEmp.getId());
                modelMapper.map(empDTO, existingEmp); 
                if (empDTO.getManagerId() != null) {
                        Employee manager = empRepository.findById(empDTO.getManagerId())
                                        .orElseThrow(() -> new ApiRequestException(
                                                        "manager not found with id" + empDTO.getManagerId()));
                        existingEmp.setManager(manager);
                } else {
                        existingEmp.setManager(null); 
                }
                if (empDTO.getDepartmentId() != null) {
                        Department dept = deptRepository.findById(empDTO.getDepartmentId())
                                        .orElseThrow(() -> new ApiRequestException(
                                                        "Invalid department id" + empDTO.getDepartmentId()));
                        existingEmp.setDepartment(dept);
                }
                Employee updatedEmp = empRepository.save(existingEmp);
                EmpDTO empDtoResponse = modelMapper.map(updatedEmp, EmpDTO.class);
                empDtoResponse.setManagerId(updatedEmp.getManager() != null ? updatedEmp.getManager().getId() : null);

                empDtoResponse.setSubordinateIds(updatedEmp.getSubordinates() != null
                                ? updatedEmp.getSubordinates().stream()
                                                .map(sub -> sub.getId()) // keep just IDs
                                                .collect(Collectors.toList())
                                : null);
                empDtoResponse.setDepartmentId(
                                updatedEmp.getDepartment() != null ? updatedEmp.getDepartment().getId() : null);
                return empDtoResponse;
        }

        // Employee views their own reviews
        public List<PerformReviewDTO> getMyReviews(Long id) {
                Employee employee = empRepository.findById(id)
                                .orElseThrow(() -> new ApiRequestException("employee not found"));
                return employee.getReceivedReviews().stream()
                                .map(review -> {
                                        PerformReviewDTO dto = modelMapper.map(review, PerformReviewDTO.class);
                                
                                        dto.setEmployeeId(review.getEmployee().getId());
                                        dto.setReviewerId(review.getReviewer().getId());
                                        return dto;
                                })
                                .collect(Collectors.toList());
        }

      
        public List<PerformReviewDTO> getReviewsWrittenByMe(Long id) {  
                Employee reviewer = empRepository.findById(id)
                        .orElseThrow(()-> new ApiRequestException("Reveiwer not found with ID"+ id));
                List<PerformanceReview> writtenReviews = performReviewRepository.findByReviewerId(reviewer.getId());

                return writtenReviews.stream()
                                .map(rev ->{
                                        PerformReviewDTO reviewDto = modelMapper.map(rev, PerformReviewDTO.class);
                                       reviewDto.setEmployeeId(rev.getEmployee().getId());
                                       reviewDto.setReviewerId(rev.getReviewer().getId());
                                       return reviewDto;
                                })
                                .collect(Collectors.toList());
        }


}

// POST
// {
// "firstName": "Rahma",
// "lastName": "Suleiman",
// "email": "rahma.suleiman@example.com",
// "phone": "+255712345678",
// "address": "Mombasa",
// "gender": "Female",
// "dob": "1998-04-15",
// "hireDate": "2023-06-01",
// "position": "Software Engineer",
// "salary": 1200000,
// "status": "ACTIVE",
// "departmentId": 2
// }
// {
// "firstName": "Amina",
// "lastName": "Hassan",
// "email": "amina.hassan@example.com",
// "phone": "+255712345678",
// "address": "Dar es Salaam",
// "gender": "Female",
// "dob": "1995-06-12",
// "hireDate": "2023-03-15",
// "position": "Software Engineer",
// "salary": 1500000,
// "status": "ACTIVE",
// "managerId": 1,
// "departmentId": 3
// }

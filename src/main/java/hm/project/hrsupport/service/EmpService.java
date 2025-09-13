package hm.project.hrsupport.service;

import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.EmpDTO;
import hm.project.hrsupport.dto.PerformReviewDTO;
import hm.project.hrsupport.entity.Department;
import hm.project.hrsupport.entity.Employee;
import hm.project.hrsupport.entity.PerformanceReview;
import hm.project.hrsupport.enums.EmployeeStatusEnum;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.DeptRepository;
import hm.project.hrsupport.repository.EmpRepository;
import hm.project.hrsupport.repository.PerformReviewRepository;
import jakarta.transaction.Transactional;
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

                                        // Set manager ID
                                        empDto.setManagerId(emp.getManager() != null ? emp.getManager().getId() : null);
                                        // Set subordinates IDs
                                        empDto.setSubordinateIds(emp.getSubordinates() != null
                                                        ? emp.getSubordinates().stream()
                                                                        // .map(sub -> sub.getId()) // just ids instead
                                                                        // of full mapping
                                                                        .map(Employee::getId)
                                                                        .collect(Collectors.toList())
                                                        // : null);
                                                        : List.of()); // empty list instead of null

                                        // Set department ID
                                        empDto.setDepartmentId(emp.getDepartment() != null ? emp.getDepartment().getId()
                                                        : null);
                                        // Set written reviews IDs
                                        empDto.setWrittenReviewIds(emp.getWrittenReviews() != null
                                                        ? emp.getWrittenReviews().stream()
                                                                        .map(PerformanceReview::getId)
                                                                        .collect(Collectors.toList())
                                                        : List.of());
                                        // Set received reviews IDs
                                        empDto.setReceivedReviewIds(emp.getReceivedReviews() != null
                                                        ? emp.getReceivedReviews().stream()
                                                                        .map(PerformanceReview::getId)
                                                                        // .map(rev-> rev.getId())
                                                                        .collect(Collectors.toList())
                                                        : List.of());
                                        //only set recruitmentId if present
                                        empDto.setRecruitmentId(emp.getRecruitment() != null ? emp.getRecruitment().getId() : null);                                       return empDto;
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
                //only set recruitmentId if present
                empDtoResponse.setRecruitmentId(emp.getRecruitment() != null ? emp.getRecruitment().getId() : null); 
                return empDtoResponse;
        }

        public EmpDTO createEmployee(EmpDTO empDTO) {
                Employee employee = modelMapper.map(empDTO, Employee.class);

                Employee manager = null;
                // Handle manager (optional)
                if (empDTO.getManagerId() != null) {
                        manager = empRepository.findById(empDTO.getManagerId())
                                        .orElseThrow(() -> new ApiRequestException(
                                                        "Manager not found with id" + empDTO.getManagerId()));
                        employee.setManager(manager);

                        manager.getSubordinates().add(employee);
                        empRepository.save(manager);
                }
                // Department required
                if (empDTO.getDepartmentId() == null) {
                        throw new ApiRequestException("Department is required for every employee");
                }
                //set DepartmentId
                Department department = deptRepository.findById(empDTO.getDepartmentId())
                                .orElseThrow(() -> new ApiRequestException("Department not found"));

                //set both sides of relation
                employee.setDepartment(department); //employee has its department set
                department.getEmployees().add(employee); //The department knows about the new employee.

                //set hired data
                employee.setHireDate(LocalDate.now());

                Employee savedEmployee = empRepository.save(employee);
 
                // map response 
                EmpDTO empResponse = modelMapper.map(savedEmployee, EmpDTO.class);

                empResponse.setManagerId(
                                savedEmployee.getManager() != null ? savedEmployee.getManager().getId() : null);

                empResponse.setDepartmentId(savedEmployee.getDepartment() != null
                                ? savedEmployee.getDepartment().getId()
                                : null);
                //only set recruitmentId if present
                empResponse.setRecruitmentId(savedEmployee.getRecruitment() != null ? savedEmployee.getRecruitment().getId() : null); 
                return empResponse;
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
                                                // .map(sub -> sub.getId()) // keep just IDs
                                                .map(Employee::getId) // keep just IDs
                                                .collect(Collectors.toList())
                                : null);
                empDtoResponse.setDepartmentId(updatedEmp.getDepartment() != null ? updatedEmp.getDepartment().getId() : null);
                //only set recruitmentId if present
                empDtoResponse.setRecruitmentId(updatedEmp.getRecruitment() != null ? updatedEmp.getRecruitment().getId() : null); 
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
                                .orElseThrow(() -> new ApiRequestException("Reveiwer not found with ID" + id));
                List<PerformanceReview> writtenReviews = performReviewRepository.findByReviewerId(reviewer.getId());

                return writtenReviews.stream()
                                .map(rev -> {
                                        PerformReviewDTO reviewDto = modelMapper.map(rev, PerformReviewDTO.class);
                                        reviewDto.setEmployeeId(rev.getEmployee().getId());
                                        reviewDto.setReviewerId(rev.getReviewer().getId());
                                        return reviewDto;
                                })
                                .collect(Collectors.toList());
        }

        // RECOMMENDED
        // ✅ Instead, you would SOFT DELETE (mark as INACTIVE or TERMINATED) so history
        // stays intact.
        // This performs a soft delete (marks employee as INACTIVE instead of removing
        // them permanently

        @Transactional
        public void deactivateEmployee(Long id) {
                Employee emp = empRepository.findById(id)
                                .orElseThrow(() -> new ApiRequestException("Employee not found with id " + id));

                if (emp.getStatus() == EmployeeStatusEnum.INACTIVE) {
                        throw new ApiRequestException("Employee with id " + id + " is already inactive");
                }
                emp.setStatus(EmployeeStatusEnum.INACTIVE);
                empRepository.save(emp);  
        }
        
        @Transactional
        public void activateEmployee(Long id) {
                Employee emp = empRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Employee not found with id " + id));
                
                if (emp.getStatus() == EmployeeStatusEnum.ACTIVE) {
                        throw new ApiRequestException("Employee with id " + id + " is already active");
                }
                emp.setStatus(EmployeeStatusEnum.ACTIVE);
                empRepository.save(emp);
        }


        // Not really. Employees are usually never HARD-DELETED, because you need
        // their history (who worked, reviews, promotions, etc.).

        // Delete employee → automatically delete recruitment + children
        // BUT make sure in employee entity all FK with @OneToMany(mappedBy = "",
        // cascade = CascadeType.ALL, orphanRemoval = true)
        // AND in recruitment u shld hv smthing like this
        // @OneToOne(mappedBy = "recruitment", cascade = CascadeType.ALL, orphanRemoval=
        // true)
        @Transactional
        public void deleteEmployeeById(Long id) {
                Employee emp = empRepository.findById(id)
                        .orElseThrow(()-> new ApiRequestException("Employee not found with ID: " + id));
                empRepository.delete(emp); // Cascade will remove subordinates and reviews
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

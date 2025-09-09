package hm.project.hrsupport.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.RecruitmentDTO;
import hm.project.hrsupport.entity.Application;
import hm.project.hrsupport.entity.Employee;
import hm.project.hrsupport.entity.Recruitment;
import hm.project.hrsupport.enums.EmployeeStatusEnum;
import hm.project.hrsupport.enums.RecruitmentStatusEnum;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.ApplicationRepository;
import hm.project.hrsupport.repository.EmpRepository;
import hm.project.hrsupport.repository.RecruitmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final ApplicationRepository applicationRepository;
    private final EmpRepository empRepository;
    private final ModelMapper modelMapper;

    // public RecruitmentDTO addRecruitment(RecruitmentDTO recruitmentDTO) {

    // Recruitment recruit = modelMapper.map(recruitmentDTO, Recruitment.class);

    // Application application =
    // applicationRepository.findById(recruitmentDTO.getApplicationId())
    // .orElseThrow(() -> new ApiRequestException("application not found"));

    // recruit.setApplication(application);

    // Recruitment savedRecruitment = recruitmentRepository.save(recruit);

    // Employee newEmployee = null;

    // // If decision = HIRED → create Employee from Application
    // if (recruitmentDTO.getStatus() == RecruitmentStatusEnum.HIRED) {
    // newEmployee = modelMapper.typeMap(Application.class, Employee.class)
    // .addMappings(mapper -> {
    // mapper.map(Application::getFirstName, Employee::setFirstName);
    // mapper.map(Application::getLastName, Employee::setLastName);
    // mapper.map(Application::getEmail, Employee::setEmail);
    // mapper.map(Application::getPhone, Employee::setPhone);
    // mapper.map(Application::getAddress, Employee::setAddress);
    // mapper.map(Application::getGender, Employee::setGender);
    // mapper.map(Application::getDob, Employee::setDob);
    // })
    // .map(application);

    // newEmployee.setHireDate(recruitmentDTO.getInterviewDate());
    // newEmployee.setStatus(EmployeeStatusEnum.ACTIVE);

    // // Link employee to recruitment(forward r/ship)employee knows which
    // recruitment they came from.
    // newEmployee.setRecruitment(savedRecruitment);
    // empRepository.save(newEmployee);

    // // Also link recruitment to employee(reverse r/ship)recruitment knows which
    // employee was hired from it.
    // savedRecruitment.setEmployee(newEmployee);
    // recruitmentRepository.save(savedRecruitment);

    // }

    // RecruitmentDTO recruitDtoResponse = modelMapper.map(savedRecruitment,
    // RecruitmentDTO.class);
    // recruitDtoResponse.setEmployeeId(newEmployee != null? newEmployee.getId():
    // null);
    // return recruitDtoResponse;
    // }
    
// @Transactional
// public RecruitmentDTO addRecruitment(RecruitmentDTO recruitmentDTO) {

//     // Map DTO → Recruitment
//     Recruitment recruit = modelMapper.map(recruitmentDTO, Recruitment.class);

//     // Fetch application
//     Application application = applicationRepository.findById(recruitmentDTO.getApplicationId())
//             .orElseThrow(() -> new ApiRequestException(
//                     "Application not found with ID " + recruitmentDTO.getApplicationId()));

//     recruit.setApplication(application);

//     // Save recruitment first
//     Recruitment savedRecruitment = recruitmentRepository.save(recruit);

//     Employee newEmployee = null;

//     // If HIRED → create Employee from Application
//     if (recruitmentDTO.getStatus() == RecruitmentStatusEnum.HIRED) {
//         // Map Application → Employee
//         newEmployee = modelMapper.map(application, Employee.class);

//         // Set extra fields manually
//         newEmployee.setHireDate(recruitmentDTO.getInterviewDate()); // make sure types match
//         newEmployee.setStatus(EmployeeStatusEnum.ACTIVE);

//         // Link employee → recruitment
//         newEmployee.setRecruitment(savedRecruitment);

//         // Save employee
//         Employee savedEmployee = empRepository.save(newEmployee);

//         // Link recruitment → employee
//         savedRecruitment.setEmployee(savedEmployee);
//         recruitmentRepository.save(savedRecruitment);
//     }

//     // Map back to DTO
//     RecruitmentDTO recruitDtoResponse = modelMapper.map(savedRecruitment, RecruitmentDTO.class);
//     recruitDtoResponse.setEmployeeId(newEmployee != null ? newEmployee.getId() : null);

//     return recruitDtoResponse;
// }

    public RecruitmentDTO addRecruitment(RecruitmentDTO recruitmentDTO) {

        Recruitment recruit = modelMapper.map(recruitmentDTO, Recruitment.class);

        Application application = applicationRepository.findById(recruitmentDTO.getApplicationId())
                .orElseThrow(() -> new ApiRequestException("application not found"));

        recruit.setApplication(application);

        Recruitment savedRecruitment = recruitmentRepository.save(recruit);

        Employee newEmployee = null;

        // If decision = HIRED → create Employee from Application
        if (recruitmentDTO.getStatus() == RecruitmentStatusEnum.HIRED) {
            newEmployee = modelMapper.map(application, Employee.class);

            newEmployee.setHireDate(recruitmentDTO.getInterviewDate()); // make sure types match
            newEmployee.setStatus(EmployeeStatusEnum.ACTIVE);

            // Link employee to recruitment(forward r/ship)employee knows which recruitment
            // they came from.
            newEmployee.setRecruitment(savedRecruitment);
            empRepository.save(newEmployee);

            // Also link recruitment to employee(reverse r/ship)recruitment knows which
            // employee was hired from it.
            savedRecruitment.setEmployee(newEmployee);
            recruitmentRepository.save(savedRecruitment);

        }

        RecruitmentDTO recruitDtoResponse = modelMapper.map(savedRecruitment, RecruitmentDTO.class);
        recruitDtoResponse.setEmployeeId(newEmployee != null ? newEmployee.getId() : null);
        return recruitDtoResponse;
    }

    public List<RecruitmentDTO> getAllRecruits() {
        List<Recruitment> recruits = recruitmentRepository.findAll();
        return recruits.stream()
                .map(rec -> modelMapper.map(rec, RecruitmentDTO.class))
                .collect(Collectors.toList());

    }

    public RecruitmentDTO getRecruitById(Long id) {
        Recruitment recruitment = recruitmentRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Recruitment not found with id" + id));
        return modelMapper.map(recruitment, RecruitmentDTO.class);
    }

    public RecruitmentDTO editRecruitment(Long id, RecruitmentDTO recruitmentDTO) {
        Recruitment recruitment = recruitmentRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Recruitment not found"));
        recruitmentDTO.setId(recruitment.getId());

        modelMapper.map(recruitmentDTO, recruitment);

        if (recruitmentDTO.getApplicationId() != null) {
            Application app = applicationRepository.findById(recruitmentDTO.getApplicationId())
                    .orElseThrow(() -> new ApiRequestException("Application not found"));
            recruitment.setApplication(app);
        }
        Recruitment savedRecruitment = recruitmentRepository.save(recruitment);
        RecruitmentDTO recruitDtoResponse = modelMapper.map(savedRecruitment, RecruitmentDTO.class);
        return recruitDtoResponse;
    }

    public void deleteRecruitment(Long id) {
        recruitmentRepository.deleteById(id);
    }

}

// {
// "remarks": "Candidate shows strong skills in Java and Spring Boot",
// "interviewDate": "2025-09-15",
// "status": "HIRED",
// "applicationId": 1
// }

// {
// "remarks": "Candidate impressed panel, offer accepted",
// "interviewDate": "2025-09-10",
// "status": "HIRED",
// "applicationId": 3
// }
// {
// "remarks": "Excellent communication and technical skills, hired for senior
// role",
// "interviewDate": "2025-09-12",
// "status": "HIRED",
// "applicationId": 4
// }

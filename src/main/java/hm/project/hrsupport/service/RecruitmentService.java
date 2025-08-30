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
    // .orElseThrow(()-> new ApiRequestException("application not found"));
    // recruit.setApplication(application);
    // Recruitment savedRecruitment = recruitmentRepository.save(recruit);
    // return modelMapper.map(savedRecruitment, RecruitmentDTO.class);
    // }
    public RecruitmentDTO addRecruitment(RecruitmentDTO recruitmentDTO) {
        Application application = applicationRepository.findById(recruitmentDTO.getApplicationId())
                .orElseThrow(() -> new ApiRequestException("application not found"));

        Recruitment recruit = modelMapper.map(recruitmentDTO, Recruitment.class);

        recruit.setApplication(application);
        Recruitment savedRecruitment = recruitmentRepository.save(recruit);
        // If decision = HIRED → map Application → Employee
        if (recruitmentDTO.getStatus() == RecruitmentStatusEnum.HIRED) {
            Employee newEmployee = modelMapper.map(application, Employee.class);
            newEmployee.setHireDate(recruitmentDTO.getInterviewDate());
            newEmployee.setStatus(EmployeeStatusEnum.ACTIVE);
            
            empRepository.save(newEmployee);

        }
        return modelMapper.map(savedRecruitment, RecruitmentDTO.class);
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
// "department": "Human Resources",
// "postedDate": "2025-08-28",
// "closingDate": "2025-09-15",
// "status": "OPEN",
// "numberOfVacancy": 3,
// "jobTitle": "HR Assistant",
// "jobDescription": "Responsible for supporting recruitment, onboarding, and
// employee welfare activities. The candidate should have good communication and
// organizational skills."
// }
// {
// "department": "Information Technology",
// "postedDate": "2025-08-20T08:30:00Z",
// "closingDate": "2025-09-10T17:00:00Z",
// "status": "OPEN",
// "numberOfVacancy": 2,
// "jobTitle": "Software Engineer",
// "jobDescription": "Develop and maintain web applications using Java Spring
// Boot and React. Experience with PostgreSQL is an added advantage."
// }

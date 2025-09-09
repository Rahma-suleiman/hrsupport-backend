package hm.project.hrsupport.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.ApplicationDTO;
import hm.project.hrsupport.entity.Application;
import hm.project.hrsupport.entity.JobPosting;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.ApplicationRepository;
import hm.project.hrsupport.repository.JobPostingRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ModelMapper modelMapper;

    public ApplicationDTO addApplication(ApplicationDTO applicationDTO) {

        Application application = modelMapper.map(applicationDTO, Application.class);

        Optional<Application> applicantExist = applicationRepository.findByFirstNameAndLastNameAndEmail(applicationDTO.getFirstName(), applicationDTO.getLastName(), applicationDTO.getEmail());
        if (applicantExist.isPresent()) {
            throw new ApiRequestException("applicant with name " + applicationDTO.getFirstName() + " " + applicationDTO.getLastName() + " and email "+ applicationDTO.getEmail() + " already applied");
        }

        if (applicationDTO.getJobPostingId() != null) {
            JobPosting jobPost = jobPostingRepository.findById(applicationDTO.getJobPostingId())
                    .orElseThrow(() -> new ApiRequestException("Job Posting id not found"));
            application.setJobPosting(jobPost);
            ;
        }
        application.setApplicationDate(LocalDateTime.now());
        Application savedApplication = applicationRepository.save(application);

        ApplicationDTO appDtoResponse = modelMapper.map(savedApplication, ApplicationDTO.class);

        return appDtoResponse;

    }

    public ApplicationDTO getApplicationById(Long id) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("application not found with id " + id));
        return modelMapper.map(app, ApplicationDTO.class);
    }

    public List<ApplicationDTO> getAllApplications() {
        List<Application> application = applicationRepository.findAll();
        return application.stream()
                .map(app -> modelMapper.map(app, ApplicationDTO.class))
                .collect(Collectors.toList());
    }

    public ApplicationDTO editApplication(Long id, ApplicationDTO applicationDTO) {
        Application existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("application not found with ID" + id));

        applicationDTO.setId(existingApplication.getId());

        modelMapper.map(applicationDTO, existingApplication);

        if (applicationDTO.getJobPostingId() != null) {
            JobPosting jobPost = jobPostingRepository.findById(applicationDTO.getJobPostingId())
                    .orElseThrow(() -> new ApiRequestException(
                            "Job Posting not found with ID" + applicationDTO.getJobPostingId()));
            existingApplication.setJobPosting(jobPost);
        }

        Application savedApplication = applicationRepository.save(existingApplication);
        ApplicationDTO appDtoResponse = modelMapper.map(savedApplication, ApplicationDTO.class);
        return appDtoResponse;
    }

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

}


// {
//   "firstName": "Shuab",
//   "lastName": "Moha",
//   "email": "moha.@example.com",
//   "phone": "+255712345678",
//   "address": "string",
//   "gender": "string",
//   "dob": "2025-09-09",
//   "status": "SUBMITTED",
//   "jobPostingId": 1
// }
//  {
//     "firstName": "Kauthar",
//     "lastName": "Pongwa",
//     "email": "kau.@example.com",
//     "phone": "+255712345128",
//     "address": "Abla",
//     "gender": "FEMALE",
//     "dob": "2025-09-09",
//     "status": "SUBMITTED",
//     "jobPostingId": 1
//   }
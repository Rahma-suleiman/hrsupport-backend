package hm.project.hrsupport.service;

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

        Optional<Application> applicantExist = applicationRepository.findByApplicantName(applicationDTO.getApplicantName());
        if (applicantExist.isPresent()) {
            throw new ApiRequestException("applicant with id " + applicationDTO.getApplicantName() + " already applied");
        }

        if (applicationDTO.getJobPostingId() != null) {
            JobPosting jobPost = jobPostingRepository.findById(applicationDTO.getJobPostingId())
                    .orElseThrow(() -> new ApiRequestException("Job Posting id not found"));
            application.setJobPosting(jobPost);
            ;
        }

        Application savedApplication = applicationRepository.save(application);

        ApplicationDTO appDtoResponse = modelMapper.map(savedApplication, ApplicationDTO.class);

        return appDtoResponse;

    }

    public ApplicationDTO getApplicationById(Long id) {
        Application app = applicationRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("application not found with id" + id));
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
// "id": 1,
// "applicantName": "John Doe",
// "email": "johndoe@example.com",
// "phone": "+255712345678",
// "applicationDate": "2025-08-29",
// "status": "SUBMITTED",
// "jobPostingId": 1
// }
// {
// "applicantName": "Amina Hassan",
// "email": "amina.hassan@example.com",
// "phone": "+255713456789",
// "applicationDate": "2025-08-29",
// "status": "SUBMITTED",
// "jobPostingId": 2
// }


package hm.project.hrsupport.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.ApplicationDTO;
import hm.project.hrsupport.entity.Application;
import hm.project.hrsupport.entity.Recruitment;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.ApplicationRepository;
import hm.project.hrsupport.repository.RecruitmentRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ModelMapper modelMapper;

    public ApplicationDTO addApplication(ApplicationDTO applicationDTO) {

        Application application = modelMapper.map(applicationDTO, Application.class);

        if (applicationDTO.getRecruitmentId() != null) {
            Recruitment recruit = recruitmentRepository.findById(applicationDTO.getRecruitmentId())
                    .orElseThrow(() -> new ApiRequestException("recruitment id not found"));
            application.setRecruitment(recruit);
        } else {
            throw new ApiRequestException("Recruitment is requered for every application");
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
            .orElseThrow(()-> new ApiRequestException("application not found with ID" + id));

        applicationDTO.setId(existingApplication.getId());

            modelMapper.map(applicationDTO, existingApplication);
            
            if (applicationDTO.getRecruitmentId() != null) {
                Recruitment recruit = recruitmentRepository.findById(applicationDTO.getRecruitmentId())
                        .orElseThrow(()-> new ApiRequestException("recruitment not found with ID" + applicationDTO.getRecruitmentId()));
                existingApplication.setRecruitment(recruit);
            }

            Application savedApplication = applicationRepository.save(existingApplication);
            ApplicationDTO appDtoResponse = modelMapper.map(savedApplication, ApplicationDTO.class);
            return appDtoResponse;
    }

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

}

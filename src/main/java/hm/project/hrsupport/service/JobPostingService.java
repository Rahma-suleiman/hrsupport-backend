package hm.project.hrsupport.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.JobPostingDTO;
import hm.project.hrsupport.entity.Department;
import hm.project.hrsupport.entity.JobPosting;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.DeptRepository;
import hm.project.hrsupport.repository.JobPostingRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;
    private final DeptRepository deptRepository;
    private final ModelMapper modelMapper;

    public JobPostingDTO postJob(JobPostingDTO jobPostingDTO) {
        JobPosting jobPost = modelMapper.map(jobPostingDTO, JobPosting.class);

        Department dept = deptRepository.findById(jobPostingDTO.getDepartmentId())
                .orElseThrow(() -> new ApiRequestException("department not found"));
        jobPost.setDepartment(dept);
        JobPosting savedJobPost = jobPostingRepository.save(jobPost);
        JobPostingDTO jobPostResponse = modelMapper.map(savedJobPost, JobPostingDTO.class);
        return jobPostResponse;
    }

    public List<JobPostingDTO> getAllJobPost() {
        List<JobPosting> jobPosts = jobPostingRepository.findAll();
        return jobPosts.stream()
                .map(jobPost -> {
                    JobPostingDTO jobPosting = modelMapper.map(jobPost, JobPostingDTO.class);
                    jobPosting.setApplicationIds(jobPost.getApplications() != null
                            ? jobPost.getApplications().stream()
                                    .map(app -> app.getId())
                                    .collect(Collectors.toList())
                            : null);
                    // If your JobPostingDTO has departmentId but your JobPosting entity has a Department
                    // object, ModelMapper won’t automatically map deparment.id(i.e obj) to
                    // departmentId.
                    jobPosting.setDepartmentId(jobPost.getDepartment().getId());
                    return jobPosting;
                })
                .collect(Collectors.toList());
    }

    public JobPostingDTO getJobPostById(Long id) {
        JobPosting job = jobPostingRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("JobPosting not found"));
        return modelMapper.map(job, JobPostingDTO.class);
    }

    public void deleteJobPosting(Long id) {
        jobPostingRepository.deleteById(id);
    }
}
// {
// "jobTitle": "Software Engineer",
// "postedDate": "2025-08-25",
// "closingDate": "2025-09-10",
// "status": "OPEN",
// "departmentId": 2,
// "jobDescription": "Responsible for designing, developing, and maintaining software applications.",
// "numberOfVacancy": 3
// }
// {
// "jobTitle": "Human Resource Officer",
// "postedDate": "2025-08-28",
// "closingDate": "2025-09-05",
// "status": "OPEN",
// "departmentId": 1,
// "jobDescription": "Manage recruitment, employee relations, and HR policies.",
// "numberOfVacancy": 2
// }

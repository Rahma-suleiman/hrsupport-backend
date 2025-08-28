package hm.project.hrsupport.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.PerformReviewDTO;
import hm.project.hrsupport.entity.Employee;
import hm.project.hrsupport.entity.PerformanceReview;
import hm.project.hrsupport.exception.ApiRequestException;
import hm.project.hrsupport.repository.EmpRepository;
import hm.project.hrsupport.repository.PerformReviewRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PerformReviewService {

    private final PerformReviewRepository performReviewRepository;
    private final EmpRepository empRepository;
    private final ModelMapper modelMapper;

    public List<PerformReviewDTO> getAllReviews() {
        List<PerformanceReview> reviews = performReviewRepository.findAll();
        return reviews.stream()
                .map(review-> modelMapper.map(review, PerformReviewDTO.class))
                .collect(Collectors.toList());
    }

    public PerformReviewDTO createReview(PerformReviewDTO performReviewDTO) {
        PerformanceReview review = modelMapper.map(performReviewDTO, PerformanceReview.class);

        Employee emp = empRepository.findById(performReviewDTO.getEmployeeId())
                .orElseThrow(()-> new ApiRequestException("employee not found"));
        review.setEmployee(emp);
                
        Employee reviewer = empRepository.findById(performReviewDTO.getReviewerId())
                .orElseThrow(()-> new ApiRequestException("reviwer not found"));
        review.setReviewer(reviewer);

        PerformanceReview savedReview = performReviewRepository.save(review);

        PerformReviewDTO reviewDtoResponse = modelMapper.map(savedReview, PerformReviewDTO.class);

        return reviewDtoResponse;
    }

    public PerformReviewDTO getReviewById(Long id) {
        PerformanceReview review = performReviewRepository.findById(id)
                    .orElseThrow(()-> new ApiRequestException("review not found"));
        return modelMapper.map(review, PerformReviewDTO.class);
    }

	public PerformReviewDTO editReview(Long id, PerformReviewDTO reviewDTO) {
        PerformanceReview existingReview = performReviewRepository.findById(id)
                    .orElseThrow(()-> new ApiRequestException("review not found"));
        modelMapper.map(reviewDTO,existingReview);
        reviewDTO.setId(existingReview.getId());

        if (reviewDTO.getEmployeeId() != null) {
            Employee employee = empRepository.findById(reviewDTO.getEmployeeId())
                        .orElseThrow(()-> new ApiRequestException("employee not found"));
            existingReview.setEmployee(employee);
        }

        if (reviewDTO.getReviewerId() != null) {
            Employee reviewer = empRepository.findById(reviewDTO.getReviewerId())
                        .orElseThrow(()-> new ApiRequestException("reviewer not found"));
            existingReview.setReviewer(reviewer);
        }
        PerformanceReview savedReview = performReviewRepository.save(existingReview);
        
        PerformReviewDTO reviewDtoResponse = modelMapper.map(savedReview, PerformReviewDTO.class);

        return reviewDtoResponse;
	}

    public void deleteReview(Long id) {
        performReviewRepository.deleteById(id);
    }

    


}

// {
//   "reviewDate": "2025-06-30",
//   "comments": "Employee has shown excellent teamwork and exceeded performance expectations.",
//   "rating": "EXCELLENT",
//   "employeeId": 2,
//   "reviewerId": 1
// }
// {
//   "reviewDate": "2025-07-30",
//   "comments": "Amina has shown excellent leadership and collaboration skills in the past quarter.",
//   "rating": "EXCELLENT",
//   "employeeId": 2,
//   "reviewerId": 1
// }

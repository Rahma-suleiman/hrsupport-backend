package hm.project.hrsupport.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.dto.RecruitmentDTO;
import hm.project.hrsupport.entity.Recruitment;
import hm.project.hrsupport.repository.RecruitmentRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final ModelMapper modelMapper;

    public RecruitmentDTO addRecruitment(RecruitmentDTO recruitmentDTO) {
        Recruitment recruit = modelMapper.map(recruitmentDTO, Recruitment.class);
        Recruitment savedRecruitment = recruitmentRepository.save(recruit);
        return modelMapper.map(savedRecruitment, RecruitmentDTO.class);
    }
    

}

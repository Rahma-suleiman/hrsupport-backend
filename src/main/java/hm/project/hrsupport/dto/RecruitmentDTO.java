package hm.project.hrsupport.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import hm.project.hrsupport.enums.RecruitmentStatusEnum;
import lombok.Data;

@Data
public class RecruitmentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String remarks;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate interviewDate;

    private RecruitmentStatusEnum status;

    private Long applicationId;


}

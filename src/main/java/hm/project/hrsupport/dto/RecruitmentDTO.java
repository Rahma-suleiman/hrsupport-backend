package hm.project.hrsupport.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import hm.project.hrsupport.enums.RecruitmentStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class RecruitmentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String JobTitle;
    private String JobDescription;
    private String department;

    private Date postedDate;
    private Date closingDate;

    @Enumerated(EnumType.STRING)
    private RecruitmentStatusEnum status;

    private Integer numberOfVacancy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> applicationIds;
}

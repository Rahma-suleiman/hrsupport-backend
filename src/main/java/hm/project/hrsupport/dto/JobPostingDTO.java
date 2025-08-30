package hm.project.hrsupport.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import hm.project.hrsupport.enums.JobPostingEnum;
import lombok.Data;

@Data
public class JobPostingDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String jobTitle;

    private String JobDescription;

    private Integer NumberOfVacancy;
    private Date postedDate;

    private Date closingDate;

    private JobPostingEnum status;

    // reverse r/ship
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> applicationIds;

    // fk
    private Long departmentId;
}

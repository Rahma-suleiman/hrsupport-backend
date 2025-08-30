package hm.project.hrsupport.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import hm.project.hrsupport.enums.ApplicationStatusEnum;
import lombok.Data;

@Data
public class ApplicationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String applicantName;
    private String email;
    private String phone;

    // private String resumeLink;
    
    private Date applicationDate;

    private ApplicationStatusEnum status;
    // fk
    private Long jobPostingId;
}

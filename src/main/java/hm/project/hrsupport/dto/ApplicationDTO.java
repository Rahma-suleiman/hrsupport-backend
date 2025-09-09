package hm.project.hrsupport.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import hm.project.hrsupport.enums.ApplicationStatusEnum;
import lombok.Data;

@Data
public class ApplicationDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dob;
    
    // private String resumeLink;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime applicationDate;


    private ApplicationStatusEnum status;
    // fk
    private Long jobPostingId;
}

package hm.project.hrsupport.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import hm.project.hrsupport.enums.LeaveStatusEnum;
import hm.project.hrsupport.enums.LeaveTypeEnum;

import lombok.Data;

@Data
public class LeaveRequestDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private LeaveTypeEnum leaveType;

    private LeaveStatusEnum status;

    private Date startDate;
    private Date endDate;

    private String reason;

    private Long employeeId;

}

package hm.project.hrsupport.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import hm.project.hrsupport.enums.AttendanceStatusEnum;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonPropertyOrder({ "id", "date", "checkInTime", "checkOutTime", "status", "employeeId"})
public class AttendDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    private AttendanceStatusEnum status;


    private Long employeeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "HH:mm")
    public LocalTime getCheckInTime() {
        if (status == AttendanceStatusEnum.ABSENT || status == AttendanceStatusEnum.ON_LEAVE) {
            return null; 
        }
        return checkInTime;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "HH:mm")
    public LocalTime getCheckOutTime() {
        if (status == AttendanceStatusEnum.ABSENT || status == AttendanceStatusEnum.ON_LEAVE) {
            return null;
        }
        return checkOutTime;
    }
}


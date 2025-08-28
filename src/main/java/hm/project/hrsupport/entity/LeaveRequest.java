package hm.project.hrsupport.entity;

import java.util.Date;

import hm.project.hrsupport.enums.LeaveStatusEnum;
import hm.project.hrsupport.enums.LeaveTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "leave_request")
@Entity
public class LeaveRequest extends AuditModel<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LeaveTypeEnum leaveType;

    @Enumerated(EnumType.STRING)
    private LeaveStatusEnum status;

    private Date startDate;
    private Date endDate;

    private String reason;
    
    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;

}

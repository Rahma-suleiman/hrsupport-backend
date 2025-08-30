package hm.project.hrsupport.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import hm.project.hrsupport.enums.RecruitmentStatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "recruitment")
public class Recruitment extends AuditModel<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String remarks;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate interviewDate;

    @Enumerated(EnumType.STRING)
    private RecruitmentStatusEnum status;

    @OneToOne
    @JoinColumn(name = "applicationId", nullable = false)
    private Application application;

}

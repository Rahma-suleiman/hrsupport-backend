package hm.project.hrsupport.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import hm.project.hrsupport.enums.ApplicationStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "application")
@Entity
public class Application extends AuditModel<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicantName;
    private String email;
    private String phone;

    // private String resumeLink;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date applicationDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatusEnum status;
    
    @ManyToOne
    @JoinColumn(name = "jobPostingId")
    private JobPosting jobPosting;
    
}

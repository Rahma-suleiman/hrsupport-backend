package hm.project.hrsupport.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import hm.project.hrsupport.enums.ApplicationStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String gender;
    
    @Column(nullable = false)
    private LocalDate dob;

    // private String resumeLink;

    // private Date applicationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime applicationDate;


    @Enumerated(EnumType.STRING)
    private ApplicationStatusEnum status;
    
    @ManyToOne
    @JoinColumn(name = "jobPostingId")
    private JobPosting jobPosting;
    
}

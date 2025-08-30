package hm.project.hrsupport.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import hm.project.hrsupport.enums.JobPostingEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTitle;

    private String JobDescription;

    private Integer NumberOfVacancy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date postedDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date closingDate;
    

    @Enumerated(EnumType.STRING)
    private JobPostingEnum status; 

    // reverse r/ship
    @OneToMany(mappedBy = "jobPosting")
    private List<Application> applications = new ArrayList<>();

    // fk
    @ManyToOne
    @JoinColumn(name = "departmentId", nullable = false)
    private Department department;
}

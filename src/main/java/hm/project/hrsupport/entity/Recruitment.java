package hm.project.hrsupport.entity;

import java.util.Date;
import java.util.List;

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

    private String JobTitle;
    private String JobDescription;
    private String department;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date postedDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date closingDate;

    @Enumerated(EnumType.STRING)
    private RecruitmentStatusEnum status;

    private Integer NumberOfVacancy;

    // reverse r/ship
    @OneToMany(mappedBy = "recruitment" )
    private List<Application> application;
}

package hm.project.hrsupport.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import hm.project.hrsupport.enums.EmployeeStatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
// import lombok.Data;
// import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// @Data
@Getter
@Setter
@Entity
// @EqualsAndHashCode(callSuper = false)
@Table(name = "employee")
public class Employee extends AuditModel<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private LocalDate dob;
    private LocalDate hireDate;
    private String position; // manager, CEO, software Developer
    private Integer salary;

    @Enumerated(EnumType.STRING)
    private EmployeeStatusEnum status;

    // Self-reference: Many employees can report to one manager
    @ManyToOne
    @JoinColumn(name = "managerId") // FK column
    private Employee manager;

    // FK
    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;
    // Reverse side: One manager has many subordinates
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> subordinates = new ArrayList<>();

    // Reviews where this employee is being reviewed
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerformanceReview> receivedReviews = new ArrayList<>();

    // Reviews written by this employee as reviewer
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerformanceReview> writtenReviews = new ArrayList<>();

    // Only employees hired through recruitment have a recruitment reference
    @OneToOne
    @JoinColumn(name="recruitmentId")
    private Recruitment recruitment;

}

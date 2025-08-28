package hm.project.hrsupport.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import hm.project.hrsupport.enums.AttendanceStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// @Data
@Getter
@Setter
@Table(name = "attendance")
@Entity
public class Attendance extends AuditModel<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    @Enumerated(EnumType.STRING) // Store enum as a as text/readable string in DB
    private AttendanceStatusEnum status; // only enum values are allowed

    @ManyToOne
    @JoinColumn(name = "employeeId") // <-- snake_case to match DB column
    private Employee employee;

}

// @Table(name="attendance")
// @Data
// @Entity
// @EqualsAndHashCode(callSuper = false)
// public class Attendance extends AuditModel<String> {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;

// @JsonFormat(pattern = "yyyy-MM-dd") // ensure proper JSON serialization
// private LocalDate date;

// @JsonFormat(pattern = "HH:mm") // format for time
// private LocalTime checkInTime;

// @JsonFormat(pattern = "HH:mm") // format for time
// private LocalTime checkOutTime;

// @Enumerated(EnumType.STRING) // store enum as text
// private AttendanceStatusEnum status;

// @ManyToOne
// @JoinColumn(name = "employee_id")
// private Employee employee;
// }

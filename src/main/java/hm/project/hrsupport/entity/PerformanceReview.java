package hm.project.hrsupport.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import hm.project.hrsupport.enums.RatingReviewEnum;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "review")
public class PerformanceReview extends AuditModel<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date reviewDate;

    private String comments;

    @Enumerated(EnumType.STRING)
    private RatingReviewEnum rating;

    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;

    // Reviewer (often the manager, but can be anyone in Employee table)
    @ManyToOne
    @JoinColumn(name = "reviewerId", nullable = false)
    private Employee reviewer;
}

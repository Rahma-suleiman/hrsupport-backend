package hm.project.hrsupport.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import hm.project.hrsupport.enums.RatingReviewEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PerformReviewDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Date reviewDate;

    private String comments;

    @NotNull(message = "Rating is required")
    private RatingReviewEnum rating;

    private Long employeeId;

    private Long reviewerId;
}

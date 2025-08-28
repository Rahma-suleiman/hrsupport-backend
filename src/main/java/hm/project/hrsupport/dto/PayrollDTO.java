package hm.project.hrsupport.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import hm.project.hrsupport.enums.PaymentStatusEnum;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PayrollDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  // @JsonFormat(pattern = "yyyy-MM")
  // private YearMonth month;
  private Integer year;
  private Integer month; 

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Integer salary;

  @PositiveOrZero
  private Integer bonus;
  
  @PositiveOrZero
  private Integer deduction;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Integer netSalary;

  private PaymentStatusEnum paymentStatus;

  // FK 
  private Long employeeId;
}

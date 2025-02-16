package com.unibuc.EmployeeManagementApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.unibuc.EmployeeManagementApp.constants.PerformanceConstants.NOT_BLANK_CONSTRAINT_MESSAGE;

//Salary POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryDto {

    private Long id;

    @NotBlank(message = NOT_BLANK_CONSTRAINT_MESSAGE)
    private BigDecimal amount;

    @NotBlank(message = NOT_BLANK_CONSTRAINT_MESSAGE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate lastPaidDate;

    private EmployeeDto employee;

}

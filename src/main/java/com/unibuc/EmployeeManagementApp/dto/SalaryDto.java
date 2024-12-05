package com.unibuc.EmployeeManagementApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

//Salary POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryDto {

    private Long id;

    private BigDecimal amount;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate lastPaidDate;

    private EmployeeDto employee;

}

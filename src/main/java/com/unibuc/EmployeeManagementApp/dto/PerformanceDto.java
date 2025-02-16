package com.unibuc.EmployeeManagementApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import static com.unibuc.EmployeeManagementApp.constants.PerformanceConstants.*;

//Performance POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceDto {

    private Long id;

    @NotBlank(message = NOT_BLANK_CONSTRAINT_MESSAGE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate reviewDate;

    @Min(value=1, message = RATING_MIN_CONSTRAINT_MESSAGE)
    @Max(value =5, message = RATING_MAX_CONSTRAINT_MESSAGE )
    private Integer rating;

    @NotBlank(message = NOT_BLANK_CONSTRAINT_MESSAGE)
    private String comments;

    private EmployeeDto employee;

}

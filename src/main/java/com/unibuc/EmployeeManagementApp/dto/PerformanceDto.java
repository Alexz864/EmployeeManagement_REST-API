package com.unibuc.EmployeeManagementApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//Performance POJO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceDto {

    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate reviewDate;

    private Integer rating;

    private String comments;

    private EmployeeDto employee;

}

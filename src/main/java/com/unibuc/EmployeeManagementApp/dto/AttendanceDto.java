package com.unibuc.EmployeeManagementApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

//Attendance POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDto {

    private Long id;

    private EmployeeDto employee;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate attendanceDate;

    private Boolean present;

}

package com.unibuc.EmployeeManagementApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unibuc.EmployeeManagementApp.model.Leave;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

//Leave POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveDto {

    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    private String reason;

    private EmployeeDto employee;

    private Leave.LeaveStatus status;

    public enum LeaveStatus {
        PENDING,    // The leave request is awaiting approval
        APPROVED,   // The leave request has been approved
        REJECTED    // The leave request has been rejected
    }

}

package com.unibuc.EmployeeManagementApp.dto;

import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Leave;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// Leave POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveDto {

    private Long id;

    private LocalDate startDate;

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

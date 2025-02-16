package com.unibuc.EmployeeManagementApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unibuc.EmployeeManagementApp.model.Leave;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import static com.unibuc.EmployeeManagementApp.constants.LeaveConstants.REASON_NOT_BLANK_CONSTRAINT_MESSAGE;
import static com.unibuc.EmployeeManagementApp.constants.PerformanceConstants.NOT_BLANK_CONSTRAINT_MESSAGE;

//Leave POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("unused")
public class LeaveDto {

    private Long id;

    @NotBlank(message = NOT_BLANK_CONSTRAINT_MESSAGE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @NotBlank(message = NOT_BLANK_CONSTRAINT_MESSAGE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    @NotBlank(message = REASON_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String reason;

    private EmployeeDto employee;

    private Leave.LeaveStatus status;

    public enum LeaveStatus {
        PENDING,    // The leave request is awaiting approval
        APPROVED,   // The leave request has been approved
        REJECTED    // The leave request has been rejected
    }

}

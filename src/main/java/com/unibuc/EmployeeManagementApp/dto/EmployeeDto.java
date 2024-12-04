package com.unibuc.EmployeeManagementApp.dto;

import com.unibuc.EmployeeManagementApp.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

//Employee POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String designation;

    private String department;

    private RoleDto role;

    //Change to DTO
    private Salary salary;

    //Change to DTO
    private List<Attendance> attendances;

    //Change to DTO
    private List<Leave> leaves;

    //Change to DTO
    private List<Performance> performances;

    //Change to DTO
    private User user;

}

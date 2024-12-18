package com.unibuc.EmployeeManagementApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}

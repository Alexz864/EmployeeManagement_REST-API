package com.unibuc.EmployeeManagementApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//User POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private RoleDto role;

    private EmployeeDto employee;

}

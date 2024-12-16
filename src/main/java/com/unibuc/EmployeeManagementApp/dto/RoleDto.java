package com.unibuc.EmployeeManagementApp.dto;

import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

//Role POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {

    private Long id;

    private String roleName;

    private List<EmployeeDto> employees;

    private List<UserDto> users;

}

package com.unibuc.EmployeeManagementApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.unibuc.EmployeeManagementApp.constants.EmployeeConstants.*;

//Employee POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    private Long id;

    @NotBlank(message = FIRST_NAME_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String firstName;

    @NotBlank(message = LAST_NAME_NOT_BLANK_CONSTRAINT_MESSAGE)
    private String lastName;

    @Email(message = VALID_EMAIL_CONSTRAINT_MESSAGE)
    private String email;

    private String designation;

    private String department;

    private RoleDto role;

}

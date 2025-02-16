package com.unibuc.EmployeeManagementApp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static com.unibuc.EmployeeManagementApp.constants.RoleConstants.ROLE_NOT_EMPTY_CONSTRAINT_MESSAGE;

//Role POJO
@Data   //Implements @Getter, @Setter and @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {

    private Long id;

    @NotEmpty(message = ROLE_NOT_EMPTY_CONSTRAINT_MESSAGE)
    private String roleName;

}

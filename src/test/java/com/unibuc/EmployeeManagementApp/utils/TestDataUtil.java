package com.unibuc.EmployeeManagementApp.utils;

import com.unibuc.EmployeeManagementApp.dto.EmployeeDto;
import com.unibuc.EmployeeManagementApp.dto.RoleDto;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Role;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static Role createTestRoleA() {
        return Role.builder()
                .id(1L)
                .roleName("Admin")
                .build();
    }

    public static Role createTestRoleB() {
        return Role.builder()
                .id(2L)
                .roleName("Manager")
                .build();
    }

    public static Role createTestRoleC() {
        return Role.builder()
                .id(3L)
                .roleName("HR")
                .build();
    }

    public static Employee createTestEmployeeEntityA(final Role role) {
        return Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johnDoe@gmail.com")
                .department("IT")
                .designation("Developer")
                .role(role)
                .build();
    }

    public static EmployeeDto createTestEmployeeDtoA(final RoleDto role) {
        return EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johnDoe@gmail.com")
                .department("IT")
                .designation("Developer")
                .role(role)
                .build();
    }

}
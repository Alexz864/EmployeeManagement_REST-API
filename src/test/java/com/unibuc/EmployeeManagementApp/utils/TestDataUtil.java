package com.unibuc.EmployeeManagementApp.utils;

import com.unibuc.EmployeeManagementApp.dto.*;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Role;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static RoleDto createTestRoleDtoA() {
        return RoleDto.builder()
                .id(1L)
                .roleName("Admin")
                .build();
    }

    public static EmployeeDto createTestEmployeeDtoA(final RoleDto roleDto) {
        return EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johnDoe@gmail.com")
                .department("IT")
                .designation("Developer")
                .role(roleDto)
                .build();
    }

    public static SalaryDto createTestSalaryDtoA(final EmployeeDto employeeDto) {
        return SalaryDto.builder()
                .amount(BigDecimal.valueOf(5000))
                .lastPaidDate(LocalDate.now())
                .employee(employeeDto)
                .build();
    }

    public static UserDto createTestUserDtoA(final RoleDto roleDto, final EmployeeDto employeeDto) {
        return UserDto.builder()
                .username("admin")
                .password("root")
                .role(roleDto)
                .employee(employeeDto)
                .build();
    }

    public static AttendanceDto createTestAttendanceDtoA(final EmployeeDto employeeDto) {
        return AttendanceDto.builder()
                .employee(employeeDto)
                .attendanceDate(LocalDate.now())
                .present(true)
                .build();
    }

}

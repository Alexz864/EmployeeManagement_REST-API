package com.unibuc.EmployeeManagementApp.utils;

import com.unibuc.EmployeeManagementApp.dto.*;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Leave;
import com.unibuc.EmployeeManagementApp.model.Role;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static Role createTestRoleEntityA() {
        return Role.builder()
                .roleName("Admin")
                .build();
    }

    public static RoleDto createTestRoleDtoA() {
        return RoleDto.builder()
                .id(1L)
                .roleName("Admin")
                .build();
    }

    public static Employee createTestEmployeeEntityA(Role role) {
        return Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john2doe@gmail.com")
                .designation("Web Developer")
                .department("IT")
                .role(role)
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

    public static LeaveDto createTestLeaveDtoA(final EmployeeDto employeeDto) {
        return LeaveDto.builder()
                .employee(employeeDto)
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2024, 12, 20))
                .reason("Medical appointment")
                .status(Leave.LeaveStatus.PENDING)
                .build();
    }

    public static PerformanceDto createTestPerformanceDtoA(final EmployeeDto employeeDto) {
        return PerformanceDto.builder()
                .employee(employeeDto)
                .reviewDate(LocalDate.now())
                .rating(5)
                .comments("Good work")
                .build();
    }

    public static Role createTestRoleEntityB() {
        return Role.builder()
                .roleName("Manager")
                .build();
    }
}

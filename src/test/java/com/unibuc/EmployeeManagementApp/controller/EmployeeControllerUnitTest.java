package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.EmployeeDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class EmployeeControllerUnitTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private Mapper<Employee, EmployeeDto> employeeMapper;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setDesignation("Developer");

        employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setFirstName("John");
        employeeDto.setLastName("Doe");
        employeeDto.setDesignation("Developer");
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() {
        when(employeeMapper.mapFrom(employeeDto)).thenReturn(employee);
        when(employeeService.createEmployee(employee)).thenReturn(employee);
        when(employeeMapper.mapTo(employee)).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.createEmployee(employeeDto);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isEqualTo(employeeDto);
    }

    @Test
    void listEmployees_ShouldReturnAllEmployees() {
        List<Employee> employees = Collections.singletonList(employee);
        List<EmployeeDto> employeeDtos = Collections.singletonList(employeeDto);

        when(employeeService.findAllEmployees()).thenReturn(employees);
        when(employeeMapper.mapTo(employee)).thenReturn(employeeDto);

        List<EmployeeDto> result = employeeController.listEmployees();

        assertThat(result).hasSize(1);
        assertThat(result).isEqualTo(employeeDtos);
    }

    @Test
    void getEmployee_WhenExists_ShouldReturnEmployee() {
        when(employeeService.findOneEmployee(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.mapTo(employee)).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.getEmployee(1L);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(employeeDto);
    }

    @Test
    void getEmployee_WhenNotExists_ShouldReturnNotFound() {
        when(employeeService.findOneEmployee(1L)).thenReturn(Optional.empty());

        ResponseEntity<EmployeeDto> response = employeeController.getEmployee(1L);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    void partialUpdateEmployee_ShouldReturnUpdatedEmployee() {
        when(employeeMapper.mapFrom(employeeDto)).thenReturn(employee);
        when(employeeService.partialUpdateEmployee(1L, employee)).thenReturn(employee);
        when(employeeMapper.mapTo(employee)).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.partialUpdateEmployee(1L, employeeDto);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(employeeDto);
    }

    @Test
    void fullUpdateEmployee_ShouldReturnUpdatedEmployee() {
        when(employeeMapper.mapFrom(employeeDto)).thenReturn(employee);
        when(employeeService.fullUpdateEmployee(1L, employee)).thenReturn(employee);
        when(employeeMapper.mapTo(employee)).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> response = employeeController.fullUpdateEmployee(1L, employeeDto);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(employeeDto);
    }

    @Test
    void deleteEmployee_ShouldReturnNoContent() {
        doNothing().when(employeeService).deleteEmployee(1L);

        ResponseEntity<?> response = employeeController.deleteEmployee(1L);

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
    }
}

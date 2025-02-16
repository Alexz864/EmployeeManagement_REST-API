package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.EmployeeDto;
import com.unibuc.EmployeeManagementApp.dto.SalaryDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Salary;
import com.unibuc.EmployeeManagementApp.service.SalaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaryControllerUnitTest {

    @Mock
    private SalaryService salaryService;

    @Mock
    private Mapper<Salary, SalaryDto> salaryMapper;

    @InjectMocks
    private SalaryController salaryController;

    private Salary testSalary;
    private SalaryDto testSalaryDto;

    @BeforeEach
    void setUp() {
        // Initialize employee test data
        Employee testEmployee = new Employee();
        testEmployee.setId(1L);
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");

        EmployeeDto testEmployeeDto = new EmployeeDto();
        testEmployeeDto.setId(1L);
        testEmployeeDto.setFirstName("John");
        testEmployeeDto.setLastName("Doe");

        // Initialize salary test data
        testSalary = new Salary();
        testSalary.setId(1L);
        testSalary.setAmount(new BigDecimal("5000.00"));
        testSalary.setLastPaidDate(LocalDate.now());
        testSalary.setEmployee(testEmployee);

        testSalaryDto = new SalaryDto();
        testSalaryDto.setId(1L);
        testSalaryDto.setAmount(new BigDecimal("5000.00"));
        testSalaryDto.setLastPaidDate(LocalDate.now());
        testSalaryDto.setEmployee(testEmployeeDto);
    }

    @Test
    void createSalary_Success() {
        // Arrange
        when(salaryMapper.mapFrom(any(SalaryDto.class))).thenReturn(testSalary);
        when(salaryService.createSalary(any(Salary.class))).thenReturn(testSalary);
        when(salaryMapper.mapTo(any(Salary.class))).thenReturn(testSalaryDto);

        // Act
        ResponseEntity<SalaryDto> response = salaryController.createSalary(testSalaryDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testSalaryDto, response.getBody());
        assertEquals(new BigDecimal("5000.00"), Objects.requireNonNull(response.getBody()).getAmount());
        verify(salaryService, times(1)).createSalary(any(Salary.class));
        verify(salaryMapper, times(1)).mapFrom(any(SalaryDto.class));
        verify(salaryMapper, times(1)).mapTo(any(Salary.class));
    }

    @Test
    void listSalaries_Success() {
        // Arrange
        List<Salary> salaries = Collections.singletonList(testSalary);
        when(salaryService.findAllSalaries()).thenReturn(salaries);
        when(salaryMapper.mapTo(any(Salary.class))).thenReturn(testSalaryDto);

        // Act
        List<SalaryDto> result = salaryController.listSalaries();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testSalaryDto, result.getFirst());
        assertEquals(new BigDecimal("5000.00"), result.getFirst().getAmount());
        verify(salaryService, times(1)).findAllSalaries();
        verify(salaryMapper, times(1)).mapTo(any(Salary.class));
    }

    @Test
    void getSalary_Success() {
        // Arrange
        when(salaryService.findOneSalary(1L)).thenReturn(Optional.of(testSalary));
        when(salaryMapper.mapTo(testSalary)).thenReturn(testSalaryDto);

        // Act
        ResponseEntity<SalaryDto> response = salaryController.getSalary(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testSalaryDto, response.getBody());
        assertEquals(new BigDecimal("5000.00"), Objects.requireNonNull(response.getBody()).getAmount());
        verify(salaryService, times(1)).findOneSalary(1L);
        verify(salaryMapper, times(1)).mapTo(testSalary);
    }

    @Test
    void getSalary_NotFound() {
        // Arrange
        when(salaryService.findOneSalary(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<SalaryDto> response = salaryController.getSalary(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(salaryService, times(1)).findOneSalary(1L);
        verify(salaryMapper, never()).mapTo(any(Salary.class));
    }

    @Test
    void fullUpdateSalary_Success() {
        // Arrange
        when(salaryMapper.mapFrom(any(SalaryDto.class))).thenReturn(testSalary);
        when(salaryService.fullUpdateSalary(eq(1L), any(Salary.class))).thenReturn(testSalary);
        when(salaryMapper.mapTo(any(Salary.class))).thenReturn(testSalaryDto);

        // Act
        ResponseEntity<SalaryDto> response = salaryController.fullUpdateSalary(1L, testSalaryDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testSalaryDto, response.getBody());
        assertEquals(new BigDecimal("5000.00"), Objects.requireNonNull(response.getBody()).getAmount());
        verify(salaryService, times(1)).fullUpdateSalary(eq(1L), any(Salary.class));
        verify(salaryMapper, times(1)).mapFrom(testSalaryDto);
        verify(salaryMapper, times(1)).mapTo(testSalary);
    }

    @Test
    void deleteSalary_Success() {
        // Arrange
        doNothing().when(salaryService).deleteSalary(1L);

        // Act
        ResponseEntity<?> response = salaryController.deleteSalary(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(salaryService, times(1)).deleteSalary(1L);
    }
}
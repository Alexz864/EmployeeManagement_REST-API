package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.EmployeeDto;
import com.unibuc.EmployeeManagementApp.dto.PerformanceDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Performance;
import com.unibuc.EmployeeManagementApp.service.PerformanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerformanceControllerUnitTest {

    @Mock
    private PerformanceService performanceService;

    @Mock
    private Mapper<Performance, PerformanceDto> performanceMapper;

    @InjectMocks
    private PerformanceController performanceController;

    private Performance testPerformance;
    private PerformanceDto testPerformanceDto;

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

        // Initialize performance test data
        testPerformance = new Performance();
        testPerformance.setId(1L);
        testPerformance.setReviewDate(LocalDate.now());
        testPerformance.setRating(5);
        testPerformance.setComments("Excellent performance");
        testPerformance.setEmployee(testEmployee);

        testPerformanceDto = new PerformanceDto();
        testPerformanceDto.setId(1L);
        testPerformanceDto.setReviewDate(testPerformance.getReviewDate());
        testPerformanceDto.setRating(testPerformance.getRating());
        testPerformanceDto.setComments(testPerformance.getComments());
        testPerformanceDto.setEmployee(testEmployeeDto);
    }

    @Test
    void createPerformance_Success() {
        // Arrange
        when(performanceMapper.mapFrom(any(PerformanceDto.class))).thenReturn(testPerformance);
        when(performanceService.createPerformance(any(Performance.class))).thenReturn(testPerformance);
        when(performanceMapper.mapTo(any(Performance.class))).thenReturn(testPerformanceDto);

        // Act
        ResponseEntity<PerformanceDto> response = performanceController.createPerformance(testPerformanceDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testPerformanceDto, response.getBody());
        verify(performanceService, times(1)).createPerformance(any(Performance.class));
        verify(performanceMapper, times(1)).mapFrom(any(PerformanceDto.class));
        verify(performanceMapper, times(1)).mapTo(any(Performance.class));
    }

    @Test
    void listPerformances_Success() {
        // Arrange
        List<Performance> performances = Collections.singletonList(testPerformance);
        when(performanceService.findAllPerformances()).thenReturn(performances);
        when(performanceMapper.mapTo(any(Performance.class))).thenReturn(testPerformanceDto);

        // Act
        List<PerformanceDto> result = performanceController.listPerformances();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testPerformanceDto, result.getFirst());
        verify(performanceService, times(1)).findAllPerformances();
        verify(performanceMapper, times(1)).mapTo(any(Performance.class));
    }

    @Test
    void getPerformance_Success() {
        // Arrange
        when(performanceService.findOnePerformance(1L)).thenReturn(Optional.of(testPerformance));
        when(performanceMapper.mapTo(testPerformance)).thenReturn(testPerformanceDto);

        // Act
        ResponseEntity<PerformanceDto> response = performanceController.getPerformance(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPerformanceDto, response.getBody());
        verify(performanceService, times(1)).findOnePerformance(1L);
        verify(performanceMapper, times(1)).mapTo(testPerformance);
    }

    @Test
    void getPerformance_NotFound() {
        // Arrange
        when(performanceService.findOnePerformance(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<PerformanceDto> response = performanceController.getPerformance(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(performanceService, times(1)).findOnePerformance(1L);
        verify(performanceMapper, never()).mapTo(any(Performance.class));
    }

    @Test
    void fullUpdatePerformance_Success() {
        // Arrange
        when(performanceMapper.mapFrom(any(PerformanceDto.class))).thenReturn(testPerformance);
        when(performanceService.fullUpdatePerformance(eq(1L), any(Performance.class))).thenReturn(testPerformance);
        when(performanceMapper.mapTo(any(Performance.class))).thenReturn(testPerformanceDto);

        // Act
        ResponseEntity<PerformanceDto> response = performanceController.fullUpdatePerformance(1L, testPerformanceDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPerformanceDto, response.getBody());
        verify(performanceService, times(1)).fullUpdatePerformance(eq(1L), any(Performance.class));
        verify(performanceMapper, times(1)).mapFrom(testPerformanceDto);
        verify(performanceMapper, times(1)).mapTo(testPerformance);
    }

    @Test
    void deletePerformance_Success() {
        // Arrange
        doNothing().when(performanceService).deletePerformance(1L);

        // Act
        ResponseEntity<?> response = performanceController.deletePerformance(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(performanceService, times(1)).deletePerformance(1L);
    }
}
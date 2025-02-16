package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Performance;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.repository.PerformanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PerformanceServiceImplUnitTest {

    @Mock
    private PerformanceRepository performanceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private PerformanceServiceImpl performanceService;

    private Performance performance;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up Employee mock
        employee = new Employee();
        employee.setEmail("employee@gmail.com");
        employee.setFirstName("John");
        employee.setLastName("Doe");

        // Set up Performance mock
        performance = new Performance();
        performance.setReviewDate(LocalDate.parse("2025-02-16"));
        performance.setRating(4);
        performance.setComments("Great work!");
        performance.setEmployee(employee);
    }

    @Test
    void createPerformance_ShouldReturnPerformance_WhenEmployeeExists() {
        // Arrange
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        when(performanceRepository.save(performance)).thenReturn(performance);

        // Act
        Performance result = performanceService.createPerformance(performance);

        // Assert
        assertNotNull(result);
        assertEquals(performance, result);
        verify(employeeRepository).findByEmail(employee.getEmail());
        verify(performanceRepository).save(performance);
    }

    @Test
    void findAllPerformances_ShouldReturnListOfPerformances() {
        // Arrange
        when(performanceRepository.findAll()).thenReturn(List.of(performance));

        // Act
        var result = performanceService.findAllPerformances();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(performance, result.getFirst());
        verify(performanceRepository).findAll();
    }

    @Test
    void findOnePerformance_ShouldReturnPerformance_WhenExists() {
        // Arrange
        when(performanceRepository.findById(1L)).thenReturn(Optional.of(performance));

        // Act
        Optional<Performance> result = performanceService.findOnePerformance(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(performance, result.get());
        verify(performanceRepository).findById(1L);
    }

    @Test
    void findOnePerformance_ShouldReturnEmpty_WhenNotFound() {
        // Arrange
        when(performanceRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Performance> result = performanceService.findOnePerformance(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(performanceRepository).findById(1L);
    }

    @Test
    void fullUpdatePerformance_ShouldUpdateAndReturnPerformance() {
        // Arrange
        Performance updatedPerformance = new Performance();
        updatedPerformance.setReviewDate(LocalDate.parse("2025-02-17"));
        updatedPerformance.setRating(5);
        updatedPerformance.setComments("Excellent work!");

        // Make sure the employee is properly set on the updated performance
        updatedPerformance.setEmployee(employee); // Add this line to set the employee

        // Mocking repository behavior
        when(performanceRepository.findById(1L)).thenReturn(Optional.of(performance));
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        when(performanceRepository.save(performance)).thenReturn(performance);

        // Act
        Performance result = performanceService.fullUpdatePerformance(1L, updatedPerformance);

        // Assert
        assertNotNull(result);
        assertEquals(updatedPerformance.getReviewDate(), result.getReviewDate());
        assertEquals(updatedPerformance.getRating(), result.getRating());
        assertEquals(updatedPerformance.getComments(), result.getComments());
        verify(performanceRepository).findById(1L);
        verify(employeeRepository).findByEmail(employee.getEmail());
        verify(performanceRepository).save(performance);
    }

    @Test
    void fullUpdatePerformance_ShouldThrowRuntimeException_WhenPerformanceNotFound() {
        // Arrange
        Performance updatedPerformance = new Performance();
        updatedPerformance.setReviewDate(LocalDate.parse("2025-02-17"));
        updatedPerformance.setRating(5);
        updatedPerformance.setComments("Excellent work!");

        when(performanceRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> performanceService.fullUpdatePerformance(1L, updatedPerformance));
        assertEquals("Not found", exception.getMessage());
    }

    @Test
    void deletePerformance_ShouldDeletePerformance_WhenExists() {
        // Arrange
        doNothing().when(performanceRepository).deleteById(1L);

        // Act
        performanceService.deletePerformance(1L);

        // Assert
        verify(performanceRepository).deleteById(1L);
    }
}

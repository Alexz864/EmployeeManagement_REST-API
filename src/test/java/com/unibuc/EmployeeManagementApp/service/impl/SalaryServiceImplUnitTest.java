package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Salary;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.repository.SalaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalaryServiceImplUnitTest {

    @Mock
    private SalaryRepository salaryRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private SalaryServiceImpl salaryService;

    private Employee employee;
    private Salary salary;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1L, "John", "Doe", "john.doe@gmail.com", "IT", "Front-End Developer", null, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
        employee.setEmail("test@example.com");

        salary = new Salary();
        salary.setEmployee(employee);
        salary.setAmount(new BigDecimal("5000.0"));
    }

    @Test
    void createSalary_ShouldCreateSalary_WhenEmployeeExists() {
        // Arrange
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        when(salaryRepository.save(salary)).thenReturn(salary);

        // Act
        Salary createdSalary = salaryService.createSalary(salary);

        // Assert
        assertNotNull(createdSalary);
        assertEquals(employee, createdSalary.getEmployee());
        verify(employeeRepository, times(1)).findByEmail(employee.getEmail());
        verify(salaryRepository, times(1)).save(salary);
    }

    @Test
    void findAllSalaries_ShouldReturnListOfSalaries() {
        // Arrange
        when(salaryRepository.findAll()).thenReturn(List.of(salary));

        // Act
        List<Salary> salaries = salaryService.findAllSalaries();

        // Assert
        assertNotNull(salaries);
        assertEquals(1, salaries.size());
        assertEquals(salary, salaries.getFirst());
        verify(salaryRepository, times(1)).findAll();
    }

    @Test
    void findOneSalary_ShouldReturnSalary_WhenSalaryExists() {
        // Arrange
        when(salaryRepository.findById(1L)).thenReturn(Optional.of(salary));

        // Act
        Optional<Salary> foundSalary = salaryService.findOneSalary(1L);

        // Assert
        assertTrue(foundSalary.isPresent());
        assertEquals(salary, foundSalary.get());
        verify(salaryRepository, times(1)).findById(1L);
    }

    @Test
    void findOneSalary_ShouldReturnEmpty_WhenSalaryDoesNotExist() {
        // Arrange
        when(salaryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Salary> foundSalary = salaryService.findOneSalary(1L);

        // Assert
        assertFalse(foundSalary.isPresent());
        verify(salaryRepository, times(1)).findById(1L);
    }

    @Test
    void fullUpdateSalary_ShouldUpdateSalary_WhenSalaryExists() {
        // Arrange
        Salary updatedSalary = new Salary();
        updatedSalary.setAmount(new BigDecimal("6000.0")); // Correct way to set the BigDecimal amount
        updatedSalary.setLastPaidDate(salary.getLastPaidDate()); // Preserve the lastPaidDate
        updatedSalary.setEmployee(employee); // Use the same employee object for the update

        // Mock the behavior of the repositories
        when(salaryRepository.findById(1L)).thenReturn(Optional.of(salary)); // Ensure the existing salary is returned
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        when(salaryRepository.save(any(Salary.class))).thenAnswer(invocation -> {
            Salary savedSalary = invocation.getArgument(0);
            savedSalary.setId(1L);  // Ensure the ID is set correctly before saving
            return savedSalary;
        });

        // Act
        Salary updated = salaryService.fullUpdateSalary(1L, updatedSalary);

        // Assert
        assertNotNull(updated);
        assertEquals(new BigDecimal("6000.0"), updated.getAmount());  // Assert the updated amount as BigDecimal
        assertEquals(1L, updated.getId());  // Ensure the ID is preserved
        verify(salaryRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findByEmail(employee.getEmail());
        verify(salaryRepository, times(1)).save(updated);  // Ensure save is called with the correct object
    }

    @Test
    void deleteSalary_ShouldDeleteSalary_WhenSalaryExists() {
        // Arrange
        doNothing().when(salaryRepository).deleteById(1L);

        // Act
        salaryService.deleteSalary(1L);

        // Assert
        verify(salaryRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSalary_ShouldDoNothing_WhenSalaryDoesNotExist() {
        // Arrange
        doThrow(new RuntimeException("Not found")).when(salaryRepository).deleteById(1L);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> salaryService.deleteSalary(1L));
        assertEquals("Not found", exception.getMessage());
        verify(salaryRepository, times(1)).deleteById(1L);
    }
}

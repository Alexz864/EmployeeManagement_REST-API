package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.exception.EmployeeNotFoundException;
import com.unibuc.EmployeeManagementApp.exception.RoleNotFoundException;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static com.unibuc.EmployeeManagementApp.utils.TestDataUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplUnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee_Success() {

        when(roleRepository.findByRoleName("ADMIN")).thenReturn(Optional.of(role1));
        when(employeeRepository.existsByEmail("john.doe@gmail.com")).thenReturn(false);
        when(employeeRepository.save(employee1)).thenReturn(employee1);

        Employee savedEmployee = employeeService.createEmployee(employee1);

        assertNotNull(savedEmployee);
        assertEquals("John", savedEmployee.getFirstName());
        assertEquals("Doe", savedEmployee.getLastName());
        assertEquals("john.doe@gmail.com", savedEmployee.getEmail());
        assertEquals("IT", savedEmployee.getDepartment());
        assertEquals("Front-End Developer", savedEmployee.getDesignation());
        assertEquals("ADMIN", savedEmployee.getRole().getRoleName());
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    void testCreateEmployee_RoleNotFound() {

        when(roleRepository.findByRoleName("INVALID_ROLE")).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> employeeService.createEmployee(employee1));
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testFindAllEmployees() {

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> employees = employeeService.findAllEmployees();

        assertEquals(2, employees.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testFindOneEmployee_Exists() {

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));

        Optional<Employee> foundEmployee = employeeService.findOneEmployee(1L);

        assertTrue(foundEmployee.isPresent());
        assertEquals("John", foundEmployee.get().getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testFindOneEmployee_NotFound() {

        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Employee> foundEmployee = employeeService.findOneEmployee(1L);

        assertFalse(foundEmployee.isPresent());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testPartialUpdateEmployee_Success() {

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        when(roleRepository.findByRoleName("EMPLOYEE")).thenReturn(Optional.of(role1));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        Employee result = employeeService.partialUpdateEmployee(1L, employee2);

        assertEquals("Andrew", result.getFirstName());
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    void testPartialUpdateEmployee_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Employee updatedEmployee = new Employee();
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.partialUpdateEmployee(1L, updatedEmployee));
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testFullUpdateEmployee_Success() {

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        when(roleRepository.findByRoleName("EMPLOYEE")).thenReturn(Optional.of(role2));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);

        Employee result = employeeService.fullUpdateEmployee(1L, employee2);

        assertEquals("Andrew", result.getFirstName());
        assertEquals("Back-End Developer", result.getDesignation());
        assertEquals("EMPLOYEE", result.getRole().getRoleName());

        verify(employeeRepository, times(1)).save(employee1);
    }


    @Test
    void testFullUpdateEmployee_NotFound() {
        Employee updatedEmployee = new Employee();
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.fullUpdateEmployee(1L, updatedEmployee));
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
package com.unibuc.EmployeeManagementApp.service.impl;
import com.unibuc.EmployeeManagementApp.exception.EmailExistsException;
import com.unibuc.EmployeeManagementApp.exception.EmployeeNotFoundException;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Role;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplUnitTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void createEmployee_validInput_employeeCreated() {
        Role role = new Role(1L, "Software Engineer", null, null);
        Employee employee = new Employee(1L, "John", "Doe", "john.doe@example.com", "IT", "Senior Developer", role, null, null, null, null, null);

        when(roleRepository.findByRoleName(role.getRoleName())).thenReturn(Optional.of(role));
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee(employee);

        assertNotNull(createdEmployee);
        assertEquals(employee.getFirstName(), createdEmployee.getFirstName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void createEmployee_existingEmail_throwsEmailExistsException() {
        Role role = new Role(1L, "Software Engineer", null, null);
        Employee employee = new Employee(1L, "John", "Doe", "john.doe@example.com", "IT", "Senior Developer", role, null, null, null, null, null);

        when(roleRepository.findByRoleName(role.getRoleName())).thenReturn(Optional.of(role));
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);

        assertThrows(EmailExistsException.class, () -> employeeService.createEmployee(employee));
        verify(employeeRepository, never()).save(employee);
    }

    @Test
    void findAllEmployees_employeesExist_returnsEmployeeList() {
        Role role = new Role(1L, "Software Engineer", null, null);
        Employee employee = new Employee(1L, "John", "Doe", "john.doe@example.com", "IT", "Senior Developer", role, null, null, null, null, null);
        Employee employee2 = new Employee(1L, "Max", "Doe", "max.doe@example.com", "IT", "Junior Developer", role, null, null, null, null, null);
        List<Employee> employees = List.of(employee, employee2);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> foundEmployees = employeeService.findAllEmployees();

        assertEquals(2, foundEmployees.size());
        assertEquals(employee.getFirstName(), foundEmployees.get(0).getFirstName());
        assertEquals(employee2.getFirstName(), foundEmployees.get(1).getFirstName());
    }

    @Test
    void findOneEmployee_employeeExists_returnsEmployee() {
        Long id = 1L;
        Role role = new Role(1L, "Software Engineer", null, null);
        Employee employee = new Employee(1L, "John", "Doe", "john.doe@example.com", "IT", "Senior Developer", role, null, null, null, null, null);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Optional<Employee> foundEmployee = employeeService.findOneEmployee(id);

        assertTrue(foundEmployee.isPresent());
        assertEquals(employee.getFirstName(), foundEmployee.get().getFirstName());
    }

    @Test
    void findOneEmployee_employeeNotFound_returnsEmptyOptional() {
        Long id = 1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Employee> foundEmployee = employeeService.findOneEmployee(id);

        assertTrue(foundEmployee.isEmpty());
    }

    @Test
    void fullUpdateEmployee_employeeExists_employeeUpdated() {
        Long id = 1L;
        Role role = new Role(1L, "Software Engineer", null, null);
        Employee existingEmployee = new Employee(1L, "John", "Doe", "john.doe@example.com", "IT", "Senior Developer", role, null, null, null, null, null);
        Employee updatedEmployee = new Employee(1L, "Jane", "Smith", "jane.smith@example.com", "Sales", "Team Lead", role, null, null, null, null, null);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(roleRepository.findByRoleName(role.getRoleName())).thenReturn(Optional.of(role));
        when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

        Employee result = employeeService.fullUpdateEmployee(id, updatedEmployee);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("jane.smith@example.com", result.getEmail());
        assertEquals("Team Lead", result.getDesignation());
        assertEquals("Sales", result.getDepartment());
        verify(employeeRepository, times(1)).save(updatedEmployee);
    }

    @Test
    void fullUpdateEmployee_employeeNotFound_throwsEmployeeNotFoundException() {
        Long id = 1L;
        Employee updatedEmployee = new Employee();

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.fullUpdateEmployee(id, updatedEmployee));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_employeeExists_employeeDeleted() {
        Long id = 1L;

        employeeService.deleteEmployee(id);

        verify(employeeRepository, times(1)).deleteById(id);
    }
}
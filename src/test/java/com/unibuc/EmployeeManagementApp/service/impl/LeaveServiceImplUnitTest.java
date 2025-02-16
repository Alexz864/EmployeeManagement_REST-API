package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.exception.EmployeeNotFoundException;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Leave;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
import com.unibuc.EmployeeManagementApp.repository.LeaveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaveServiceImplUnitTest {

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private LeaveServiceImpl leaveService;

    private Employee employee;
    private Leave leave1;
    private Leave leave2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1L, "John Doe", "john.doe@gmail.com", "Software Engineer", "IT", "Front-End Developer", null, null, null, null, null, null);
        leave1 = new Leave(1L, LocalDate.now(), LocalDate.now().plusDays(5), "Vacation", employee, Leave.LeaveStatus.PENDING);
        leave2 = new Leave(2L, LocalDate.now().plusDays(10), LocalDate.now().plusDays(15), "Medical", employee, Leave.LeaveStatus.APPROVED);
    }

    @Test
    void testCreateLeave_Success() {
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        when(leaveRepository.save(any(Leave.class))).thenReturn(leave1);

        Leave result = leaveService.createLeave(leave1);

        assertNotNull(result);
        assertEquals(leave1.getReason(), result.getReason());
        assertEquals(Leave.LeaveStatus.PENDING, result.getStatus());

        verify(leaveRepository, times(1)).save(leave1);
    }

    @Test
    void testCreateLeave_EmployeeNotFound() {
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> leaveService.createLeave(leave1));

        verify(leaveRepository, never()).save(any(Leave.class));
    }

    @Test
    void testFindAllLeaves() {
        when(leaveRepository.findAll()).thenReturn(Arrays.asList(leave1, leave2));

        List<Leave> leaves = leaveService.findAllLeaves();

        assertEquals(2, leaves.size());
        assertEquals("Vacation", leaves.get(0).getReason());
        assertEquals("Medical", leaves.get(1).getReason());

        verify(leaveRepository, times(1)).findAll();
    }

    @Test
    void testFindOneLeave_Success() {
        when(leaveRepository.findById(1L)).thenReturn(Optional.of(leave1));

        Optional<Leave> result = leaveService.findOneLeave(1L);

        assertTrue(result.isPresent());
        assertEquals("Vacation", result.get().getReason());

        verify(leaveRepository, times(1)).findById(1L);
    }

    @Test
    void testFindOneLeave_NotFound() {
        when(leaveRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Leave> result = leaveService.findOneLeave(99L);

        assertFalse(result.isPresent());

        verify(leaveRepository, times(1)).findById(99L);
    }

    @Test
    void testFullUpdateLeave_Success() {
        when(leaveRepository.findById(1L)).thenReturn(Optional.of(leave1));
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        when(leaveRepository.save(any(Leave.class))).thenReturn(leave1);

        Leave updatedLeave = new Leave(1L, LocalDate.now(), LocalDate.now().plusDays(7), "Extended Vacation", employee, Leave.LeaveStatus.APPROVED);

        Leave result = leaveService.fullUpdateLeave(1L, updatedLeave);

        assertEquals("Extended Vacation", result.getReason());
        assertEquals(Leave.LeaveStatus.APPROVED, result.getStatus());

        verify(leaveRepository, times(1)).save(any(Leave.class));
    }

    @Test
    void testFullUpdateLeave_LeaveNotFound() {
        when(leaveRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> leaveService.fullUpdateLeave(99L, leave1));

        verify(leaveRepository, never()).save(any(Leave.class));
    }

    @Test
    void testDeleteLeave() {
        doNothing().when(leaveRepository).deleteById(1L);

        leaveService.deleteLeave(1L);

        verify(leaveRepository, times(1)).deleteById(1L);
    }
}
package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.exception.EmployeeNotFoundException;
import com.unibuc.EmployeeManagementApp.model.Attendance;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.repository.AttendanceRepository;
import com.unibuc.EmployeeManagementApp.repository.EmployeeRepository;
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

class AttendanceServiceImplUnitTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AttendanceServiceImpl attendanceService;

    private Employee employee;
    private Attendance attendance1;
    private Attendance attendance2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee(1L, "John", "Doe", "john.doe@gmail.com", "Developer", "IT", null, null, null, null, null,null);
        attendance1 = new Attendance(1L, employee, LocalDate.now(), true);
        attendance2 = new Attendance(2L, employee, LocalDate.now().minusDays(1), false);
    }

    @Test
    void testCreateAttendance_Success() {
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        when(attendanceRepository.save(attendance1)).thenReturn(attendance1);

        Attendance result = attendanceService.createAttendance(attendance1);

        assertNotNull(result);
        assertEquals(employee, result.getEmployee());
        verify(attendanceRepository, times(1)).save(attendance1);
    }

    @Test
    void testCreateAttendance_EmployeeNotFound() {
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> attendanceService.createAttendance(attendance1));
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    void testFindAllAttendances_Success() {
        when(attendanceRepository.findAll()).thenReturn(Arrays.asList(attendance1, attendance2));

        List<Attendance> result = attendanceService.findAllAttendances();

        assertEquals(2, result.size());
        verify(attendanceRepository, times(1)).findAll();
    }

    @Test
    void testFindOneAttendance_Success() {
        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(attendance1));

        Optional<Attendance> result = attendanceService.findOneAttendance(1L);

        assertTrue(result.isPresent());
        assertEquals(attendance1, result.get());
        verify(attendanceRepository, times(1)).findById(1L);
    }

    @Test
    void testFindOneAttendance_NotFound() {
        when(attendanceRepository.findById(3L)).thenReturn(Optional.empty());

        Optional<Attendance> result = attendanceService.findOneAttendance(3L);

        assertFalse(result.isPresent());
        verify(attendanceRepository, times(1)).findById(3L);
    }

    @Test
    void testFullUpdateAttendance_Success() {
        Attendance updatedAttendance = new Attendance(null, employee, LocalDate.now(), false);

        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(attendance1));
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance1);

        Attendance result = attendanceService.fullUpdateAttendance(1L, updatedAttendance);

        assertEquals(false, result.getPresent());
        assertEquals(employee, result.getEmployee());
        verify(attendanceRepository, times(1)).save(attendance1);
    }

    @Test
    void testFullUpdateAttendance_EmployeeNotFound() {
        Attendance updatedAttendance = new Attendance(null, employee, LocalDate.now(), false);

        when(attendanceRepository.findById(1L)).thenReturn(Optional.of(attendance1));
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> attendanceService.fullUpdateAttendance(1L, updatedAttendance));
        verify(attendanceRepository, never()).save(any(Attendance.class));
    }

    @Test
    void testDeleteAttendance_Success() {
        doNothing().when(attendanceRepository).deleteById(1L);

        attendanceService.deleteAttendance(1L);

        verify(attendanceRepository, times(1)).deleteById(1L);
    }
}
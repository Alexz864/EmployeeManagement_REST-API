package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.AttendanceDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Attendance;
import com.unibuc.EmployeeManagementApp.service.AttendanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceControllerUnitTest {

    @Mock
    private AttendanceService attendanceService;

    @Mock
    private Mapper<Attendance, AttendanceDto> attendanceMapper;

    @InjectMocks
    private AttendanceController attendanceController;

    private Attendance testAttendance;
    private AttendanceDto testAttendanceDto;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testAttendance = new Attendance();
        testAttendance.setId(1L);
        testAttendance.setAttendanceDate(LocalDate.from(LocalDateTime.now()));
        testAttendance.setPresent(true);

        testAttendanceDto = new AttendanceDto();
        testAttendanceDto.setId(1L);
        testAttendanceDto.setAttendanceDate(testAttendance.getAttendanceDate());
        testAttendanceDto.setPresent(testAttendance.getPresent());
    }

    @Test
    void createAttendance_Success() {
        // Arrange
        when(attendanceMapper.mapFrom(any(AttendanceDto.class))).thenReturn(testAttendance);
        when(attendanceService.createAttendance(any(Attendance.class))).thenReturn(testAttendance);
        when(attendanceMapper.mapTo(any(Attendance.class))).thenReturn(testAttendanceDto);

        // Act
        ResponseEntity<AttendanceDto> response = attendanceController.createAttendance(testAttendanceDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testAttendanceDto, response.getBody());
        verify(attendanceService, times(1)).createAttendance(any(Attendance.class));
    }

    @Test
    void listAttendances_Success() {
        // Arrange
        List<Attendance> attendances = Collections.singletonList(testAttendance);
        when(attendanceService.findAllAttendances()).thenReturn(attendances);
        when(attendanceMapper.mapTo(any(Attendance.class))).thenReturn(testAttendanceDto);

        // Act
        List<AttendanceDto> result = attendanceController.listAttendances();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testAttendanceDto, result.getFirst());
        verify(attendanceService, times(1)).findAllAttendances();
    }

    @Test
    void getAttendance_Success() {
        // Arrange
        when(attendanceService.findOneAttendance(1L)).thenReturn(Optional.of(testAttendance));
        when(attendanceMapper.mapTo(testAttendance)).thenReturn(testAttendanceDto);

        // Act
        ResponseEntity<AttendanceDto> response = attendanceController.getAttendance(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testAttendanceDto, response.getBody());
    }

    @Test
    void getAttendance_NotFound() {
        // Arrange
        when(attendanceService.findOneAttendance(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AttendanceDto> response = attendanceController.getAttendance(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void fullUpdateAttendance_Success() {
        // Arrange
        when(attendanceMapper.mapFrom(any(AttendanceDto.class))).thenReturn(testAttendance);
        when(attendanceService.fullUpdateAttendance(eq(1L), any(Attendance.class))).thenReturn(testAttendance);
        when(attendanceMapper.mapTo(any(Attendance.class))).thenReturn(testAttendanceDto);

        // Act
        ResponseEntity<AttendanceDto> response = attendanceController.fullUpdateAttendance(1L, testAttendanceDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testAttendanceDto, response.getBody());
        verify(attendanceService, times(1)).fullUpdateAttendance(eq(1L), any(Attendance.class));
    }

    @Test
    void deleteAttendance_Success() {
        // Arrange
        doNothing().when(attendanceService).deleteAttendance(1L);

        // Act
        ResponseEntity<?> response = attendanceController.deleteAttendance(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(attendanceService, times(1)).deleteAttendance(1L);
    }
}
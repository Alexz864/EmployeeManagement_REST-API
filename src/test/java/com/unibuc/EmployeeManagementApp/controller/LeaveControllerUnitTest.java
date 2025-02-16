package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.LeaveDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Leave;
import com.unibuc.EmployeeManagementApp.service.LeaveService;
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
class LeaveControllerUnitTest {

    @Mock
    private LeaveService leaveService;

    @Mock
    private Mapper<Leave, LeaveDto> leaveMapper;

    @InjectMocks
    private LeaveController leaveController;

    private Leave testLeave;
    private LeaveDto testLeaveDto;

    @BeforeEach
    void setUp() {
        // Initialize test data
        testLeave = new Leave();
        testLeave.setId(1L);
        testLeave.setStartDate(LocalDate.now());
        testLeave.setEndDate(LocalDate.now().plusDays(5));
        testLeave.setReason("VACATION");
        testLeave.setStatus(Leave.LeaveStatus.PENDING);

        testLeaveDto = new LeaveDto();
        testLeaveDto.setId(1L);
        testLeaveDto.setStartDate(testLeave.getStartDate());
        testLeaveDto.setEndDate(testLeave.getEndDate());
        testLeaveDto.setReason(testLeave.getReason());
        testLeaveDto.setStatus(testLeave.getStatus());
    }

    @Test
    void createLeave_Success() {
        // Arrange
        when(leaveMapper.mapFrom(any(LeaveDto.class))).thenReturn(testLeave);
        when(leaveService.createLeave(any(Leave.class))).thenReturn(testLeave);
        when(leaveMapper.mapTo(any(Leave.class))).thenReturn(testLeaveDto);

        // Act
        ResponseEntity<LeaveDto> response = leaveController.createLeave(testLeaveDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testLeaveDto, response.getBody());
        verify(leaveService, times(1)).createLeave(any(Leave.class));
        verify(leaveMapper, times(1)).mapFrom(any(LeaveDto.class));
        verify(leaveMapper, times(1)).mapTo(any(Leave.class));
    }

    @Test
    void listLeaves_Success() {
        // Arrange
        List<Leave> leaves = Collections.singletonList(testLeave);
        when(leaveService.findAllLeaves()).thenReturn(leaves);
        when(leaveMapper.mapTo(any(Leave.class))).thenReturn(testLeaveDto);

        // Act
        List<LeaveDto> result = leaveController.listLeaves();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testLeaveDto, result.getFirst());
        verify(leaveService, times(1)).findAllLeaves();
        verify(leaveMapper, times(1)).mapTo(any(Leave.class));
    }

    @Test
    void getLeave_Success() {
        // Arrange
        when(leaveService.findOneLeave(1L)).thenReturn(Optional.of(testLeave));
        when(leaveMapper.mapTo(testLeave)).thenReturn(testLeaveDto);

        // Act
        ResponseEntity<LeaveDto> response = leaveController.getLeave(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testLeaveDto, response.getBody());
        verify(leaveService, times(1)).findOneLeave(1L);
        verify(leaveMapper, times(1)).mapTo(testLeave);
    }

    @Test
    void getLeave_NotFound() {
        // Arrange
        when(leaveService.findOneLeave(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<LeaveDto> response = leaveController.getLeave(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(leaveService, times(1)).findOneLeave(1L);
        verify(leaveMapper, never()).mapTo(any(Leave.class));
    }

    @Test
    void fullUpdateLeave_Success() {
        // Arrange
        when(leaveMapper.mapFrom(any(LeaveDto.class))).thenReturn(testLeave);
        when(leaveService.fullUpdateLeave(eq(1L), any(Leave.class))).thenReturn(testLeave);
        when(leaveMapper.mapTo(any(Leave.class))).thenReturn(testLeaveDto);

        // Act
        ResponseEntity<LeaveDto> response = leaveController.fullUpdateLeave(1L, testLeaveDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testLeaveDto, response.getBody());
        verify(leaveService, times(1)).fullUpdateLeave(eq(1L), any(Leave.class));
        verify(leaveMapper, times(1)).mapFrom(testLeaveDto);
        verify(leaveMapper, times(1)).mapTo(testLeave);
    }

    @Test
    void deleteLeave_Success() {
        // Arrange
        doNothing().when(leaveService).deleteLeave(1L);

        // Act
        ResponseEntity<?> response = leaveController.deleteLeave(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(leaveService, times(1)).deleteLeave(1L);
    }
}
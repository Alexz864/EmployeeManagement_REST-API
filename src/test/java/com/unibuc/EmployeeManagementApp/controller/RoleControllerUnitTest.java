package com.unibuc.EmployeeManagementApp.controller;

import com.unibuc.EmployeeManagementApp.dto.RoleDto;
import com.unibuc.EmployeeManagementApp.mapper.Mapper;
import com.unibuc.EmployeeManagementApp.model.Role;
import com.unibuc.EmployeeManagementApp.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(MockitoExtension.class)
class RoleControllerUnitTest {

    @Mock
    private RoleService roleService;

    @Mock
    private Mapper<Role, RoleDto> roleMapper;

    @InjectMocks
    private RoleController roleController;

    private RoleDto roleDto;
    private Role role;

    private final Role updatedRole = new Role(1L, "Updated Role Name", null, null);
    private final RoleDto updatedRoleDto = new RoleDto(1L, "Updated Role Name");


    @BeforeEach
    void setUp() {
        roleDto = new RoleDto(1L, "Admin");
        role = new Role(1L, "Admin", null, null);
    }

    @Test
    void createRole_ShouldReturnCreatedRole() {
        // Arrange
        when(roleMapper.mapFrom(roleDto)).thenReturn(role);
        when(roleService.saveRole(role)).thenReturn(role);
        when(roleMapper.mapTo(role)).thenReturn(roleDto);

        // Act
        ResponseEntity<RoleDto> response = roleController.createRole(roleDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isEqualTo(roleDto);
    }

    @Test
    void listRoles_ShouldReturnAllRoles() {
        // Arrange
        when(roleService.findAllRoles()).thenReturn(List.of(role));
        when(roleMapper.mapTo(role)).thenReturn(roleDto);

        // Act
        List<RoleDto> result = roleController.listRoles();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.getFirst()).isEqualTo(roleDto);
    }

    @Test
    void getRole_WhenRoleExists_ShouldReturnRole() {
        // Arrange
        when(roleService.findOneRole(1L)).thenReturn(Optional.of(role));
        when(roleMapper.mapTo(role)).thenReturn(roleDto);

        // Act
        ResponseEntity<RoleDto> response = roleController.getRole(1L);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(roleDto);
    }


    @Test
    void getRole_WhenRoleDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        when(roleService.findOneRole(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<RoleDto> response = roleController.getRole(999L);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void fullUpdateRole_WhenRoleExists_ShouldReturnUpdatedRole() {
        // Arrange
        when(roleService.roleExists(1L)).thenReturn(true);
        when(roleMapper.mapFrom(updatedRoleDto)).thenReturn(updatedRole);
        when(roleService.saveRole(updatedRole)).thenReturn(updatedRole);
        when(roleMapper.mapTo(updatedRole)).thenReturn(updatedRoleDto);

        // Act
        ResponseEntity<RoleDto> response = roleController.fullUpdateRole(1L, updatedRoleDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedRoleDto);
    }



    @Test
    void fullUpdateRole_WhenRoleDoesNotExist_ShouldReturnNotFound() {
        // Arrange
        Long roleId = 99L; // Assume a non-existing role ID
        when(roleService.roleExists(roleId)).thenReturn(false);

        // Act
        ResponseEntity<RoleDto> response = roleController.fullUpdateRole(roleId, updatedRoleDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void deleteRole_ShouldReturnNoContent() {
        // Arrange
        Long roleId = 1L;
        doNothing().when(roleService).deleteRole(roleId);

        // Act
        ResponseEntity<?> response = roleController.deleteRole(roleId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}

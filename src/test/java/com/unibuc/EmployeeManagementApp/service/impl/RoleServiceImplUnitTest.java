package com.unibuc.EmployeeManagementApp.service.impl;

import com.unibuc.EmployeeManagementApp.model.Role;
import com.unibuc.EmployeeManagementApp.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static com.unibuc.EmployeeManagementApp.utils.TestDataUtil.role1;
import static com.unibuc.EmployeeManagementApp.utils.TestDataUtil.role2;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplUnitTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testThatSaveRoleSuccessfullySavesNewRole() {

        when(roleRepository.save(role1)).thenReturn(role1);

        Role savedRole = roleService.saveRole(role1);

        assertNotNull(savedRole);
        assertEquals("ADMIN", savedRole.getRoleName());
        verify(roleRepository, times(1)).save(role1);
    }

    @Test
    void testThatFindAllRolesSuccessfullyReturnsAllRoles() {

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        List<Role> roles = roleService.findAllRoles();

        assertEquals(2, roles.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testThatFindOneRoleSuccessfullyReturnsTheSpecifiedRole() {

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role1));

        Optional<Role> foundRole = roleService.findOneRole(1L);

        assertTrue(foundRole.isPresent());
        assertEquals("ADMIN", foundRole.get().getRoleName());
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    void testRoleExists() {
        when(roleRepository.existsById(1L)).thenReturn(true);

        boolean exists = roleService.roleExists(1L);

        assertTrue(exists);
        verify(roleRepository, times(1)).existsById(1L);
    }

    @Test
    void testThatDeleteRoleSuccessfullyDeletesRole() {
        doNothing().when(roleRepository).deleteById(1L);

        roleService.deleteRole(1L);

        verify(roleRepository, times(1)).deleteById(1L);
    }
}
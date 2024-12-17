package com.unibuc.EmployeeManagementApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.EmployeeManagementApp.dto.RoleDto;
import com.unibuc.EmployeeManagementApp.model.Role;
import com.unibuc.EmployeeManagementApp.service.RoleService;
import com.unibuc.EmployeeManagementApp.utils.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RoleControllerIntegrationTest {

    private final RoleService roleService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public RoleControllerIntegrationTest(MockMvc mockMvc, RoleService roleService) {
        this.mockMvc = mockMvc;
        this.roleService = roleService;
        this.objectMapper = new ObjectMapper();
    }

    //Create
    @Test
    public void testThatCreateRoleSuccessfullyReturnsHttp201Created() throws Exception {
        RoleDto testRoleDtoA = TestDataUtil.createTestRoleDtoA();
        testRoleDtoA.setId(null);
        String roleJSON = objectMapper.writeValueAsString(testRoleDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJSON)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateRoleSuccessfullyReturnsSavedRole() throws Exception {
        RoleDto testRoleDtoA = TestDataUtil.createTestRoleDtoA();
        testRoleDtoA.setId(null);
        String roleJSON = objectMapper.writeValueAsString(testRoleDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.roleName").value(testRoleDtoA.getRoleName())
        );
    }

    //Read all
    @Test
    public void testThatListRolesReturnsHttp200() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListRolesReturnsListOfRoles() throws Exception {

        Role testRoleA = TestDataUtil.createTestRoleEntityA();
        roleService.createRole(testRoleA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].roleName").value("Admin")
        );
    }

    //Read one
    @Test
    public void testThatGetRoleReturnsHttp200WhenRoleExists() throws Exception {

        Role testRoleA = TestDataUtil.createTestRoleEntityA();
        roleService.createRole(testRoleA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetRoleReturnsHttp404WhenNoRoleExists() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/roles/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetRoleReturnsRoleWhenRoleExists() throws Exception {

        Role testRoleA = TestDataUtil.createTestRoleEntityA();
        roleService.createRole(testRoleA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.roleName").value("Admin")
        );
    }

}

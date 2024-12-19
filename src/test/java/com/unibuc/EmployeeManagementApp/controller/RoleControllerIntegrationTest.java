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

    //Inject MockMvc Bean in constructor
    @Autowired
    public RoleControllerIntegrationTest(MockMvc mockMvc, RoleService roleService) {
        this.mockMvc = mockMvc;
        this.roleService = roleService;
        this.objectMapper = new ObjectMapper();
    }

    //Create Tests
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

    //Read all Tests
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
        roleService.saveRole(testRoleA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].roleName").value("Admin")
        );
    }

    //Read one Tests
    @Test
    public void testThatGetRoleReturnsHttp200WhenRoleExists() throws Exception {

        Role testRoleA = TestDataUtil.createTestRoleEntityA();
        roleService.saveRole(testRoleA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetRoleReturnsRoleWhenRoleExists() throws Exception {

        Role testRoleA = TestDataUtil.createTestRoleEntityA();
        roleService.saveRole(testRoleA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.roleName").value("Admin")
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

    //Full update Tests
    @Test
    public void testThatFullUpdateRoleReturnsHttp404WhenNoRoleExists() throws Exception {

        RoleDto testRoleDtoA = TestDataUtil.createTestRoleDtoA();
        String roleDtoJson = objectMapper.writeValueAsString(testRoleDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/roles/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateRoleReturnsHttp200WhenRoleExists() throws Exception {

        Role testRoleEntityA = TestDataUtil.createTestRoleEntityA();
        Role savedRoleEntityA = roleService.saveRole(testRoleEntityA);

        RoleDto testRoleDtoA = TestDataUtil.createTestRoleDtoA();
        String roleDtoJson = objectMapper.writeValueAsString(testRoleDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/roles/" + savedRoleEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateUpdatesExistingRole () throws Exception {

        Role testRoleEntityA = TestDataUtil.createTestRoleEntityA();
        Role savedRoleEntityA = roleService.saveRole(testRoleEntityA);

        Role roleDto = TestDataUtil.createTestRoleEntityB();
        roleDto.setId(savedRoleEntityA.getId());

        String roleDtoUpdateJson = objectMapper.writeValueAsString(roleDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/roles/" + savedRoleEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedRoleEntityA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.roleName").value(roleDto.getRoleName())
        );
    }

    @Test
    public void testThatDeleteRoleReturnsHttp204ForNonExistingRole() throws  Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/roles/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteRoleReturnsHttp204ForExistingRole() throws  Exception {

        Role testRoleEntityA = TestDataUtil.createTestRoleEntityA();
        Role savedRoleEntityA = roleService.saveRole(testRoleEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/roles/" + savedRoleEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

}

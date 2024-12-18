package com.unibuc.EmployeeManagementApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.EmployeeManagementApp.dto.EmployeeDto;
import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.service.EmployeeService;
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
public class EmployeeControllerIntegrationTest {

    private final EmployeeService employeeService;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    //Inject MockMvc Bean in constructor
    @Autowired
    public EmployeeControllerIntegrationTest(MockMvc mockMvc, EmployeeService employeeService) {
        this.mockMvc = mockMvc;
        this.employeeService = employeeService;
        this.objectMapper = new ObjectMapper();
    }

    //Create Tests
    @Test
    public void testThatCreateEmployeeReturnsHttpStatus201Created() throws Exception {

        EmployeeDto testEmployeeDto = TestDataUtil.createTestEmployeeDtoA(null);

        String employeeJSON = objectMapper.writeValueAsString(testEmployeeDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJSON)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateEmployeeSuccessfullyReturnsSavedEmployee() throws Exception {
        EmployeeDto testEmployeeDto = TestDataUtil.createTestEmployeeDtoA(null);

        String employeeJSON = objectMapper.writeValueAsString(testEmployeeDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.firstName").value("John")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lastName").value("Doe")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("johnDoe@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.department").value("IT")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.designation").value("Developer")
        );
    }

    //Read all Tests
    @Test
    public void testThatListEmployeesReturnsHttpStatus200() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListEmployeesReturnsListOfEmployees() throws Exception {

        Employee testEmployeeEntityA = TestDataUtil.createTestEmployeeEntityA(null);
        employeeService.createEmployee(testEmployeeEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].firstName").value("John")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].email").value("john2doe@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].department").value("IT")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].designation").value("Web Developer")
        );
    }

    //Read one Tests
    @Test
    public void testThatGetEmployeeReturnsHttp200WhenEmployeeExist() throws  Exception{
        Employee testEmployeeEntityA = TestDataUtil.createTestEmployeeEntityA(null);
        employeeService.createEmployee(testEmployeeEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetEmployeeReturnsHttp404WhenNoEmployeeExists() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetEmployeeReturnsEmployeeWhenEmployeeExists() throws Exception {

        Employee testEmployeeEntityA = TestDataUtil.createTestEmployeeEntityA(null);
        employeeService.createEmployee(testEmployeeEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.firstName").value("John")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lastName").value("Doe")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("john2doe@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.department").value("IT")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.designation").value("Web Developer")
        );
    }

}

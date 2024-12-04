package com.unibuc.EmployeeManagementApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.EmployeeManagementApp.dto.EmployeeDto;
import com.unibuc.EmployeeManagementApp.model.Role;
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

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired  //Inject MockMvc in constructor
    public EmployeeControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

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

}

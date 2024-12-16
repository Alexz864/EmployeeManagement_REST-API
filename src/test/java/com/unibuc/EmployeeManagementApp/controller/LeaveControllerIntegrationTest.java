package com.unibuc.EmployeeManagementApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unibuc.EmployeeManagementApp.dto.LeaveDto;
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
public class LeaveControllerIntegrationTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired  //Inject MockMvc in constructor
    public LeaveControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testThatCreateLeaveReturnsHttpStatus201Created() throws Exception {

        LeaveDto testLeaveDto = TestDataUtil.createTestLeaveDtoA(null);

        String leaveJSON = objectMapper.writeValueAsString(testLeaveDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/leaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(leaveJSON)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateLeaveSuccessfullyReturnsSavedLeave() throws Exception {
        LeaveDto testLeaveDto = TestDataUtil.createTestLeaveDtoA(null);

        String leaveJSON = objectMapper.writeValueAsString(testLeaveDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/leaves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(leaveJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.startDate").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.endDate").value("2024-12-20")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.reason").value("Medical appointment")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.status").value("PENDING")
        );
    }
}

package com.unibuc.EmployeeManagementApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unibuc.EmployeeManagementApp.dto.LeaveDto;
import com.unibuc.EmployeeManagementApp.dto.PerformanceDto;
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
public class PerformanceControllerIntegrationTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired  //Inject MockMvc in constructor
    public PerformanceControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testThatCreatePerformanceReturnsHttpStatus201Created() throws Exception {

        PerformanceDto testPerformanceDto = TestDataUtil.createTestPerformanceDtoA(null);

        String performanceJSON = objectMapper.writeValueAsString(testPerformanceDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/performances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(performanceJSON)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatePerformanceSuccessfullyReturnsSavedPerformance() throws Exception {
        PerformanceDto testPerformanceDto = TestDataUtil.createTestPerformanceDtoA(null);

        String performanceJSON = objectMapper.writeValueAsString(testPerformanceDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/performances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(performanceJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.reviewDate").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.rating").value(5)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.comments").value("Good work")
        );
    }
}

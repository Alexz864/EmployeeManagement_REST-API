//package com.unibuc.EmployeeManagementApp.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.unibuc.EmployeeManagementApp.dto.UserDto;
//import com.unibuc.EmployeeManagementApp.utils.TestDataUtil;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureMockMvc
//public class UserControllerIntegrationTest {
//
//    private final MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper;
//
//    //Inject MockMvc Bean in constructor
//    @Autowired
//    public UserControllerIntegrationTest(MockMvc mockMvc) {
//        this.mockMvc = mockMvc;
//        this.objectMapper = new ObjectMapper();
//    }
//
//    //Create Tests
//    @Test
//    public void testThatCreateUserReturnsHttpStatus201Created() throws Exception {
//
//        UserDto testUserDto = TestDataUtil.createTestUserDtoA(null, null);
//
//        String userJSON = objectMapper.writeValueAsString(testUserDto);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJSON)
//        ).andExpect(
//                MockMvcResultMatchers.status().isCreated()
//        );
//    }
//
//    @Test
//    public void testThatCreateUserSuccessfullyReturnsSavedUser() throws Exception {
//        UserDto testUserDto = TestDataUtil.createTestUserDtoA(null, null);
//
//        String userJSON = objectMapper.writeValueAsString(testUserDto);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJSON)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.id").isNumber()
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.username").value("admin")
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.password").value("root")
//        );
//    }
//}

package com.unibuc.EmployeeManagementApp.utils;

import com.unibuc.EmployeeManagementApp.model.Role;

public class TestDataUtil {

    private TestDataUtil() {
    }

    public static Role createTestRoleA() {
        return Role.builder()
                .id(1L)
                .roleName("Admin")
                .build();
    }

    public static Role createTestRoleB() {
        return Role.builder()
                .id(2L)
                .roleName("Manager")
                .build();
    }

    public static Role createTestRoleC() {
        return Role.builder()
                .id(3L)
                .roleName("HR")
                .build();
    }

}

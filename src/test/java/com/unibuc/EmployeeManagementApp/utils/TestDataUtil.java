package com.unibuc.EmployeeManagementApp.utils;

import com.unibuc.EmployeeManagementApp.model.Employee;
import com.unibuc.EmployeeManagementApp.model.Role;
import java.util.ArrayList;

public class TestDataUtil {

    public static Role role1 = new Role(1L, "ADMIN", new ArrayList<>(), new ArrayList<>());
    public static Role role2 = new Role(2L, "EMPLOYEE", new ArrayList<>(), new ArrayList<>());

    public static Employee employee1 = new Employee(1L, "John", "Doe", "john.doe@gmail.com", "IT", "Front-End Developer", role1, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
    public static Employee employee2 = new Employee(2L, "Andrew", "Huston", "andrew.huston@gmail.com", "IT", "Back-End Developer", role2, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
}

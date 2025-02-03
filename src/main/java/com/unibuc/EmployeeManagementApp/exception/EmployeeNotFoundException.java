package com.unibuc.EmployeeManagementApp.exception;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(String employee) {
        super("Employee not found: " + employee);
    }
}

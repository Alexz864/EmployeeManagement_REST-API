package com.unibuc.EmployeeManagementApp.exception;

public class EmailExistsException extends RuntimeException{

    public EmailExistsException(String email) {
        super("The email: " + email + " already exists.");
    }

}

package com.unibuc.EmployeeManagementApp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@SuppressWarnings("unused")
public class ErrorResponseModel {

    private final int status;

    private final String message;

}

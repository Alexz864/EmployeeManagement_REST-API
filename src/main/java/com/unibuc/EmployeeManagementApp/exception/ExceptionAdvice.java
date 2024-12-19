package com.unibuc.EmployeeManagementApp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@SuppressWarnings("unused")
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<ErrorResponseModel> handleRoleNotFoundException(RoleNotFoundException e) {

        //Log the exception for debugging purposes
        logger.error(e.getMessage());


        //Build the structured error response
        ErrorResponseModel errorResponseModel = new ErrorResponseModel(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage()
        );

        //Return the response entity with the appropriate status and error body
        return new ResponseEntity<>(errorResponseModel, HttpStatus.NOT_FOUND);
    }

}

package org.dmdev.springbootrestapi.exceptions;

import jakarta.validation.ValidationException;
import org.dmdev.springbootrestapi.models.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(IllegalDateRangeException.class)
    public ResponseEntity<ResponseModel> handleIllegalDateRange(IllegalDateRangeException ex, WebRequest request){
        ResponseModel responseModel = ResponseModel.builder()
                .status("Bad Request: " + HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalBirthdateException.class)
    public ResponseEntity<ResponseModel> handleIllegalBirthdateException(IllegalBirthdateException ex, WebRequest request){
        ResponseModel responseModel = ResponseModel.builder()
                .status("Bad Request: " + HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResponseModel> handleHibernateValidationException(IllegalBirthdateException ex, WebRequest request){
        ResponseModel responseModel = ResponseModel.builder()
                .status("Bad Request: " + HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseModel> handleIllegalEmailFormatException(MethodArgumentNotValidException ex, WebRequest request){
        ResponseModel responseModel = ResponseModel.builder()
                .status("Bad Request: " + HttpStatus.BAD_REQUEST.value())
                .message("Email format is not valid")
                .build();
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModel> handleGeneralException(Exception ex, WebRequest request){
        ResponseModel responseModel = ResponseModel.builder()
                .status("Internal Server Error: " + HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal Server Error")
                .build();
        return new ResponseEntity<>(responseModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.people.restdemo.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Exception handlers to handle all exceptions thrown to controllers
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle Generic Exception
     *
     * @param ex      the exceptino thrown
     * @param request the exception thrown
     * @return Http 500
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles Course/Student not found Exception
     *
     * @param ex      the exception thrown
     * @param request the exceptino thrown
     * @return Http Status 400
     */
    @ExceptionHandler({CourseNotFoundException.class, StudentNotFoundException.class})
    public final ResponseEntity<ExceptionResponse> handleCourseNotFoundException(CourseNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles Invalid not found Exception
     *
     * @param ex      the exception thrown
     * @param request the exceptino thrown
     * @return Http Status 400
     */

    @ExceptionHandler(InvalidDataException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidDataException(InvalidDataException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Overridden method to throw 400 if Method Arguments Invalid,suring attribute validations
     *
     * @param ex      the exception thrown
     * @param request the exception thrown
     * @return Http Status 400
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());


        ExceptionResponse exceptionResponse = new ExceptionResponse(String.join(",", errors));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles Constraint voilation exception
     *
     * @param ex      the exception thrown
     * @param request the exceptino thrown
     * @return Http Status 400
     */

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ExceptionResponse exceptionResponse = new ExceptionResponse(String.join(",", errors));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles Data Integrity  Exception
     *
     * @param ex      the exception thrown
     * @param request the exceptino thrown
     * @return Http Status 400
     */

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> handleDataIntegrityException(DataIntegrityViolationException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                "Check if course being inserted is valid course");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles Course Update
     *
     * @param ex      the exception thrown
     * @param request the exceptino thrown
     * @return Http Status 422
     */
    @ExceptionHandler(CourseUpdateException.class)
    public final ResponseEntity<ExceptionResponse> handleCourseUpdateException(CourseUpdateException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getLocalizedMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }


}
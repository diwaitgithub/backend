package com.infy.gameszone.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.infy.gameszone.exception.GameszoneException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    Environment environment;

    @ExceptionHandler
    public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception) {
        //
        ErrorInfo error = new ErrorInfo();
        //
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setErrorMessage(environment.getProperty("General.EXCEPTION_MESSAGE"));
        error.setTimestamp(LocalDateTime.now());
        //
        return new ResponseEntity<ErrorInfo>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GameszoneException.class)
    public ResponseEntity<ErrorInfo> gameszoneExceptionHandler(GameszoneException exception) {
        //
        ErrorInfo error = new ErrorInfo();
        //
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setErrorMessage(environment.getProperty(exception.getMessage()));
        error.setTimestamp(LocalDateTime.now());
        //
        return new ResponseEntity<ErrorInfo>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
    public ResponseEntity<ErrorInfo> exceptionHandler(Exception exception) {
        //
        ErrorInfo error = new ErrorInfo();
        //
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        //
        String errorMessage = "";
        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception1 = (MethodArgumentNotValidException) exception;
            //
            errorMessage = exception1.getBindingResult().getAllErrors().stream().map(err -> err.getDefaultMessage())
                    .collect(Collectors.joining(", "));
        } else {
            ConstraintViolationException exception2 = (ConstraintViolationException) exception;
            //
            errorMessage = exception2.getConstraintViolations().stream().map(err -> err.getMessage())
                    .collect(Collectors.joining(", "));
        }
        //
        error.setErrorMessage(errorMessage);
        error.setTimestamp(LocalDateTime.now());
        //
        return new ResponseEntity<ErrorInfo>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorInfo> handleAuthenticationException(AuthenticationException ex,
            HttpServletResponse response) {
        //
        ErrorInfo error = new ErrorInfo();

        error.setErrorCode(HttpStatus.UNAUTHORIZED.value());
        error.setErrorMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());

        //
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorInfo> handleAccessDeniedException(AccessDeniedException ex,
            HttpServletResponse response) {
        //
        ErrorInfo error = new ErrorInfo();

        error.setErrorCode(HttpStatus.UNAUTHORIZED.value());
        error.setErrorMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());

        //
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}

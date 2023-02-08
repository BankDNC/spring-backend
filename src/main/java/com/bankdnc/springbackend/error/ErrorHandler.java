package com.bankdnc.springbackend.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, UsernameNotFoundException.class})
    public ProblemDetail handleMethodArgumentNotValidException(Exception ex, HttpServletRequest request){
        return ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(400), ex.getMessage());

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request){
        return ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(400), "Error en el formato de la petición");
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleUserAlreadyExistsException(UserAlreadyExistsException ex, HttpServletRequest request){
        return ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(409), ex.getMessage());
    }


}

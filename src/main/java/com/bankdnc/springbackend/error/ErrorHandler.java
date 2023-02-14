package com.bankdnc.springbackend.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(409), ex.getMessage());
    }

    @ExceptionHandler(NoTypeAccountException.class)
    public ProblemDetail handleNoTypeAccountException(NoTypeAccountException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(406), ex.getMessage());
    }

}

package com.Reservas.SistemaReservas.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExcepcionGeneral {

@ExceptionHandler(ApiExcepcion.class)
    public ResponseEntity<ProblemDetail> excepctionHandler(ApiExcepcion excepcion){

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(excepcion.getHttpStatus(),excepcion.getMessage());
    problemDetail.setTitle("Error Api");
    return ResponseEntity.status(excepcion.getHttpStatus()).body(problemDetail);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handlerValidations(MethodArgumentNotValidException excepcion){


    String messageDefault = excepcion.getBindingResult().getFieldError().getDefaultMessage();
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, messageDefault.concat( " el parametro : "+excepcion.getBindingResult().getFieldError().getField()));
    return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

}

package com.Reservas.SistemaReservas.excepcion;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class ApiExcepcion extends RuntimeException{
    private HttpStatus httpStatus;

    public ApiExcepcion(String message,HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public static ApiExcepcion userNotFound(String mensaje){
        return new ApiExcepcion(mensaje,HttpStatus.NOT_FOUND);
    }

    public static  ApiExcepcion valueInvalid(String mensaje){
        return  new ApiExcepcion(mensaje,HttpStatus.BAD_REQUEST);
    }

}

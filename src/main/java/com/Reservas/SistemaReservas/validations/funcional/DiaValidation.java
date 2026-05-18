package com.Reservas.SistemaReservas.validations.funcional;

import com.Reservas.SistemaReservas.validations.interfaz.DiaValidationI;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class DiaValidation implements ConstraintValidator<DiaValidationI,String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value==null || value.isBlank() ){
            return false;
        }

        List<String> dias = List.of("lunes",
                "martes",
                "miercoles",
                "jueves",
                "viernes",
                "sabado",
                "domingo"
        );

        if(dias.stream().anyMatch(a ->a.equals(value.toLowerCase()))){
                return true;
        };


        return true;
    }
}

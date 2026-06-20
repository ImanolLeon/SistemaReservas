package com.Reservas.SistemaReservas.validations.funcional;

import com.Reservas.SistemaReservas.validations.interfaz.LocalTimeValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.h2.constraint.Constraint;

import java.lang.annotation.Annotation;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class LocalTimeValidacionImpl implements ConstraintValidator<LocalTimeValidation, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value.isBlank()){
            return false;
        }

        try{
            LocalTime.parse(value);
            return true;

        }catch (DateTimeParseException e){
           return false;
        }

    }
}

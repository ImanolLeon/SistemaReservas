package com.Reservas.SistemaReservas.validations.funcional;

import com.Reservas.SistemaReservas.validations.interfaz.IdValidationI;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdValidationFun implements ConstraintValidator<IdValidationI,Long> {
    @Override
    public void initialize(IdValidationI constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        if(value <=0 ){
            return false;
        }

        return true;
    }
}

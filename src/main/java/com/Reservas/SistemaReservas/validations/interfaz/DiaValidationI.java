package com.Reservas.SistemaReservas.validations.interfaz;


import com.Reservas.SistemaReservas.validations.funcional.DiaValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
@Constraint(validatedBy = DiaValidation.class)
public @interface DiaValidationI {


    String message() default "{time.day}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };


}

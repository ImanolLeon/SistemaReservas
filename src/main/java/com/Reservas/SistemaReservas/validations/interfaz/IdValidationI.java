package com.Reservas.SistemaReservas.validations.interfaz;


import com.Reservas.SistemaReservas.validations.funcional.DiaValidation;
import com.Reservas.SistemaReservas.validations.funcional.IdValidationFun;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = IdValidationFun.class)
public @interface IdValidationI {

    String message() default "{id.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };


}

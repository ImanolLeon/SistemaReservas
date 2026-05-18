package com.Reservas.SistemaReservas.validations.interfaz;


import com.Reservas.SistemaReservas.validations.funcional.LocalTimeValidacionImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalTimeValidacionImpl.class)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface LocalTimeValidation {
    String message() default "{time.validation}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};
}

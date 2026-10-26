package com.Reservas.SistemaReservas.dto.request;

import com.Reservas.SistemaReservas.Entity.ReservaBalon;
import com.Reservas.SistemaReservas.Entity.ReservaCamiseta;
import com.Reservas.SistemaReservas.validations.interfaz.DiaValidationI;
import com.Reservas.SistemaReservas.validations.interfaz.LocalTimeValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservaRequest (

        Long idUsuario,

        @NotNull
        Long campoFutbol,

        @LocalTimeValidation
        String horaInicio,

        @LocalTimeValidation
        String horaFinal,

        LocalDate fecha,

        @DiaValidationI
        String dia,

        double precio,

        ReservaBalon reservaBalon,

        ReservaCamiseta reservaCamiseta


){


}

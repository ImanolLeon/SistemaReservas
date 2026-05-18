package com.Reservas.SistemaReservas.dto.request;

import com.Reservas.SistemaReservas.Entity.ReservaBalon;
import com.Reservas.SistemaReservas.Entity.ReservaCamiseta;
import com.Reservas.SistemaReservas.validations.interfaz.DiaValidationI;
import com.Reservas.SistemaReservas.validations.interfaz.LocalTimeValidation;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ReservaRequest (

        Long idUsuario,

        @NotBlank
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

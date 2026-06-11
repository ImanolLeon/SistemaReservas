package com.Reservas.SistemaReservas.dto.response;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record ReservaReponse(

        String nombreUsuario,

        String horaInicio,

        String horaFinal ,

        LocalDate fecha,

        String dia



) {
}

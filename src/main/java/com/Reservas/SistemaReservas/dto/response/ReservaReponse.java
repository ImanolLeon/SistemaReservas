package com.Reservas.SistemaReservas.dto.response;

import com.Reservas.SistemaReservas.dto.request.ReservaBalonRequest;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReservaReponse(

        Long id,
        String nombreUsuario,
        String nombreCampoFutbol,
        String horaInicio,
        String horaFinal,
        LocalDate fecha,
        String dia,
        double precio,
        String estadoReserva,
        ReservaBalonRequest reservaBalones

) {
}

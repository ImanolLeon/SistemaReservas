package com.Reservas.SistemaReservas.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservaRequestEspecification (
        LocalDate fecha,
        String dia,
        String nombreCampoFutbol,
        LocalTime horaInicio

){
}

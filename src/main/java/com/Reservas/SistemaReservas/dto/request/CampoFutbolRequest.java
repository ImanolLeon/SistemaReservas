package com.Reservas.SistemaReservas.dto.request;


import com.Reservas.SistemaReservas.Entity.Reserva;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

    public record CampoFutbolRequest(@NotBlank String nombre, @NotBlank String condicionCampo, @NotBlank String tamanoCampo,@NotBlank String superficie, List<Reserva> reservas) {
}

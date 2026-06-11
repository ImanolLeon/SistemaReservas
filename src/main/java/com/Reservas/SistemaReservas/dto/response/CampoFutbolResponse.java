package com.Reservas.SistemaReservas.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record CampoFutbolResponse (
        @NotBlank String nombre,
        @NotBlank String condicionCampo,
        @NotBlank String tamanoCampo,
        @NotBlank String superficie,
        List<ReservaReponse> reservas
){

}

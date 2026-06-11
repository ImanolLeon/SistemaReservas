package com.Reservas.SistemaReservas.dto.request;

import com.Reservas.SistemaReservas.Entity.ReservaBalon;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BalonDto(String tamanoBalon, @NotNull @NotBlank String rutaImagen, List<ReservaBalon> reservaBalones) {
}

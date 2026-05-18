package com.Reservas.SistemaReservas.dto.request;

import com.Reservas.SistemaReservas.Entity.ReservaBalon;

import java.util.List;

public record BalonDto(String tamanoBalon, String rutaImagen, List<ReservaBalon> reservaBalones) {
}

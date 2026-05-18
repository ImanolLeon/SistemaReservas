package com.Reservas.SistemaReservas.dto.request;

import com.Reservas.SistemaReservas.Entity.ReservaCamiseta;

import java.util.List;

public record CamisetaDto(String color, String rutaImagen, List<ReservaCamiseta> reservaCamisetas) {
}

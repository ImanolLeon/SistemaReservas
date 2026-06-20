package com.Reservas.SistemaReservas.dto.request;

import com.Reservas.SistemaReservas.Entity.Reserva;
import lombok.Builder;

import java.util.List;

@Builder
public record UsuarioRequest(
        String dni,
        String nombre,
        String username,
        String apellido,
        String numero,
        String email,
        String password,
        List<Reserva> reservas



) {


}

package com.Reservas.SistemaReservas.dto.response;

import java.util.List;

public record LoginResponse(
        String token,
        String tipo,
        Long id,
        String username,
        String nombre,
        String apellido,
        String email,
        List<String> roles
) {
}

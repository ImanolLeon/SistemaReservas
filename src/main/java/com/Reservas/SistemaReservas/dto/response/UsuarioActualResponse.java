package com.Reservas.SistemaReservas.dto.response;

import java.util.List;

public record UsuarioActualResponse(
        Long id,
        String username,
        String nombre,
        String apellido,
        String dni,
        String numero,
        String email,
        List<String> roles
) {
}

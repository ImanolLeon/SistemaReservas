package com.Reservas.SistemaReservas.dto.response;

import lombok.Builder;

@Builder
public record UsuarioResponse (
        String dni,
                                  String nombre,
                                  String username,
                                  String apellido,
                                  String numero,
                                  String email,
        String rol




) {

}

package com.Reservas.SistemaReservas.Services.reglas;

import com.Reservas.SistemaReservas.dto.request.UsuarioRequest;
import com.Reservas.SistemaReservas.dto.response.UsuarioResponse;

import java.util.List;

public interface UsuarioServiceImpl {
        List<UsuarioResponse> findAll();
        UsuarioResponse save(UsuarioRequest usuarioRequest);
        boolean hacerAdmin(Long id);


}

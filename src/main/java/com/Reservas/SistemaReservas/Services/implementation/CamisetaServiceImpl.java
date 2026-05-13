package com.Reservas.SistemaReservas.Services.implementation;

import com.Reservas.SistemaReservas.Entity.Camiseta;
import com.Reservas.SistemaReservas.dto.request.CamisetaDto;

import java.util.List;

public interface CamisetaServiceImpl {
    CamisetaDto save(CamisetaDto camisetaDto);
    Camiseta findById(Long id);
    List<CamisetaDto> findAll();
    boolean validarCamiseta(CamisetaDto camisetaDto);



}

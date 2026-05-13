package com.Reservas.SistemaReservas.Services.implementation;

import com.Reservas.SistemaReservas.Entity.Balon;
import com.Reservas.SistemaReservas.dto.request.BalonDto;

import java.util.List;

public interface BalonServiceImpl {
    BalonDto save(BalonDto balon);
    Balon findById(Long id);
    List<BalonDto> listAll();
    List<BalonDto> findByTamanoBalon (String balon);
    boolean validarBalon(BalonDto balon);

}

package com.Reservas.SistemaReservas.Services.reglas;

import com.Reservas.SistemaReservas.Entity.ReservaBalon;
import com.Reservas.SistemaReservas.dto.response.ReservaBalonResponse;

import java.util.List;

public interface ReservaBalonImpl {
    ReservaBalonResponse save(Long idReserva,Long idBalon);
    ReservaBalon findById(Long id);
    List<ReservaBalonResponse> listAll();
}

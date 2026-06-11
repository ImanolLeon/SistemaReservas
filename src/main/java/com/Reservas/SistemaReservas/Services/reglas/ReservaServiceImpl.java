package com.Reservas.SistemaReservas.Services.reglas;

import com.Reservas.SistemaReservas.dto.request.ReservaRequest;
import com.Reservas.SistemaReservas.Entity.Reserva;
import com.Reservas.SistemaReservas.dto.response.ReservaReponse;

import java.time.LocalTime;
import java.util.List;

public interface ReservaServiceImpl  {

    List<ReservaReponse> findAll();
    List<ReservaReponse> findByDia(String dia);
    List<ReservaReponse> findByEstadoReserva(String estado);
    ReservaRequest guardar(ReservaRequest reserva);
    Reserva buscarPorId(Long id);
    boolean cancelarReserva(Long id);
    List<ReservaReponse> findByCampoFutbol(Long id);
    List<ReservaReponse> findByHoraInicio(LocalTime horaInicio);


}

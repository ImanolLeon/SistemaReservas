package com.Reservas.SistemaReservas.Services.implementation;

import com.Reservas.SistemaReservas.dto.request.ReservaRequest;
import com.Reservas.SistemaReservas.Entity.Enum.EstadoReserva;
import com.Reservas.SistemaReservas.Entity.Reserva;

import java.util.List;
import java.util.Optional;

public interface ReservaServiceImpl  {

    List<Reserva> findAll();
    List<Reserva> findByDia(String dia);
    List<Reserva> findByEstadoReserva(EstadoReserva estado);
    ReservaRequest guardar(ReservaRequest reserva);
    Optional<Reserva> buscarPorId(Long id);
    boolean validarReserva(Reserva reserva);
    ReservaRequest cambiarEstado(Long id);

}

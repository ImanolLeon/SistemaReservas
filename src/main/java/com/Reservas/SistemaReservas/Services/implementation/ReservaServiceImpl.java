package com.Reservas.SistemaReservas.Services.implementation;

import com.Reservas.SistemaReservas.Entity.Reserva;
import com.Reservas.SistemaReservas.Repository.ReservaRepository;

import java.util.List;

public interface ReservaServiceImpl  {

    List<Reserva> findAll();
    List<Reserva> findByDia(String dia);

}

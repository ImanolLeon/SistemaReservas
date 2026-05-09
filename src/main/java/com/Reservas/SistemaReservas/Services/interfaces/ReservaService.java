package com.Reservas.SistemaReservas.Services.interfaces;

import com.Reservas.SistemaReservas.Entity.Reserva;
import com.Reservas.SistemaReservas.Repository.ReservaRepository;
import com.Reservas.SistemaReservas.Services.implementation.ReservaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService implements ReservaServiceImpl {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> findByDia(String dia) {
        return reservaRepository.findByDia(dia);
    }


}

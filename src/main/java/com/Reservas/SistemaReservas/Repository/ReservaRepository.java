package com.Reservas.SistemaReservas.Repository;

import com.Reservas.SistemaReservas.Entity.Enum.EstadoReserva;
import com.Reservas.SistemaReservas.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    //buscar reservas por dia
    List<Reserva> findByDia(String dia);
    //buscar reservas por estado
    List<Reserva> findByEstadoReserva(EstadoReserva estadoReserva);
    //buscar reserva por hora
    List<Reserva> findByHoraInicio(LocalTime horaInicio);




}

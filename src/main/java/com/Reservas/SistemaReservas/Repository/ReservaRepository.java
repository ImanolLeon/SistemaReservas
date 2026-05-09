package com.Reservas.SistemaReservas.Repository;

import com.Reservas.SistemaReservas.Entity.Enum.EstadoReserva;
import com.Reservas.SistemaReservas.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByDia(String dia);
    List<Reserva> findByEstadoReserva(EstadoReserva estadoReserva);
}

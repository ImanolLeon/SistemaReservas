package com.Reservas.SistemaReservas.Repository;

import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.Entity.Enum.EstadoReserva;
import com.Reservas.SistemaReservas.Entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    List<Reserva> findByIdCampoFutbol(CampoFutbol campoFutbol);

    @Query("""
        SELECT r from Reserva r
                where r.idCampoFutbol.id=:campoId
                        and r.fecha = :fecha
                        and  r.dia= :dia
                        and r.horaInicio<:horaFin
                        and  r.horaFinal>:horaInicio
                                
        """
    )
    List<Reserva> findReservaColapsadas(
            @Param("campoId") Long campoId,
            @Param("fecha")LocalDate fecha,
            @Param("dia") String dia,
            @Param("horaFin") LocalTime horaFin,
            @Param("horaInicio") LocalTime horaInicio
            );



}

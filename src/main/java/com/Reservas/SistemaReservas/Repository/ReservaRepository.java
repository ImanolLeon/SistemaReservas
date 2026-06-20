package com.Reservas.SistemaReservas.Repository;

import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.Entity.Enum.EstadoReserva;
import com.Reservas.SistemaReservas.Entity.Reserva;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public interface ReservaRepository extends JpaRepository<Reserva, Long>, JpaSpecificationExecutor<Reserva> {
    //buscar reservas por dia
    List<Reserva> findByDia(String dia);

    //buscar reservas por estado
    List<Reserva> findByEstadoReserva(EstadoReserva estadoReserva);

    //buscar reserva por hora
    List<Reserva> findByHoraInicio(LocalTime horaInicio);

    List<Reserva> findByIdCampoFutbol(CampoFutbol campoFutbol);

    @Query("select count(r) from Reserva r where r.camisetas.id = :id and r.horaInicio<:horaFinal and r.horaFinal>:horaInicio and r.dia = :dia and r.fecha = :fecha")
    Long findCruceReservaCamiseta(@Param("id") Long id ,
                                  @Param("horaFinal") LocalTime horaFinal ,
                                  @Param("horaInicio") LocalTime horaInicio,
                                  @Param("dia") String dia,
                                  @Param("fecha") LocalDate fecha
    );


    @Query(
            "select count(r) from Reserva r where r.id= :id and r.balones is not null"
    )
    long buscarReservaBalonEnReserva(@Param("id") Long idReserva);

    @Query(
            "SELECT COUNT(r) FROM Reserva r " +
                    "WHERE r.balones.idBalon.idBalon = :idBalon " +
                    "AND r.horaInicio < :horaFinal " +
                    "AND r.horaFinal > :horaInicio " +
                    "AND r.dia = :dia and r.fecha =:fecha"
    )
    Long contarCrucesReservaBalon(@Param("idBalon") Long idBalon,
                                  @Param("horaFinal") LocalTime horaFinal,
                                  @Param("horaInicio") LocalTime horaInicio,
                                  @Param("dia") String dia,
                                  @Param("fecha") LocalDate fecha
                                  );


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
            @Param("fecha") LocalDate fecha,
            @Param("dia") String dia,
            @Param("horaFin") LocalTime horaFin,
            @Param("horaInicio") LocalTime horaInicio
    );


}

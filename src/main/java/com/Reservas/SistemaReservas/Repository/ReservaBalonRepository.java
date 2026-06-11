package com.Reservas.SistemaReservas.Repository;

import com.Reservas.SistemaReservas.Entity.ReservaBalon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface ReservaBalonRepository extends JpaRepository<ReservaBalon, Long> {


}

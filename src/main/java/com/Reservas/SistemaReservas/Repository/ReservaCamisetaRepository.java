package com.Reservas.SistemaReservas.Repository;

import com.Reservas.SistemaReservas.Entity.ReservaCamiseta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaCamisetaRepository extends JpaRepository<ReservaCamiseta, Integer> {


}

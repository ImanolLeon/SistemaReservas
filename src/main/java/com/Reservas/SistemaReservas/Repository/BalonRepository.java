package com.Reservas.SistemaReservas.Repository;

import com.Reservas.SistemaReservas.Entity.Balon;
import com.Reservas.SistemaReservas.Entity.Enum.TamanoBalon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalonRepository extends JpaRepository<Balon,Long> {
    List<Balon> findByBalon (TamanoBalon balon);

}

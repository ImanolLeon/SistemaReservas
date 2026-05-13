package com.Reservas.SistemaReservas.Repository;

import com.Reservas.SistemaReservas.Entity.Camiseta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CamisetaRepository extends JpaRepository<Camiseta, Long> {
    @Override
    Optional<Camiseta> findById(Long Long);
}

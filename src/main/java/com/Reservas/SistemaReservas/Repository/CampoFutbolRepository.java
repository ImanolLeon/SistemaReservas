package com.Reservas.SistemaReservas.Repository;

import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.Entity.Enum.CondicionCampo;
import com.Reservas.SistemaReservas.Entity.Enum.SuperficieCampo;
import com.Reservas.SistemaReservas.Entity.Enum.TamanoCampo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampoFutbolRepository extends JpaRepository<CampoFutbol, Long> {

    List<CampoFutbol> findByTamanoCampo(TamanoCampo tamanoCampo);

    List<CampoFutbol> findBySuperficieCampo(SuperficieCampo superficieCampo);

    List<CampoFutbol> findByCondicionCampo(CondicionCampo condicionCampo);

}

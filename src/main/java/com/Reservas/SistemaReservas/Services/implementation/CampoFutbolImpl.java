package com.Reservas.SistemaReservas.Services.implementation;

import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.Entity.Enum.CondicionCampo;
import com.Reservas.SistemaReservas.Entity.Enum.SuperficieCampo;
import com.Reservas.SistemaReservas.Entity.Enum.TamanoCampo;
import com.Reservas.SistemaReservas.dto.request.CampoFutbolRequest;


import java.util.List;
import java.util.Optional;

public interface CampoFutbolImpl {
    List<CampoFutbolRequest> listAll ();
    CampoFutbolRequest save(CampoFutbolRequest campoFutbolRequest);
    CampoFutbol findById(Long id);
    List<CampoFutbolRequest> findByTamanoCampo(String tamanoCampo);

    List<CampoFutbolRequest> findBySuperficieCampo(String superficieCampo);

    List<CampoFutbolRequest> findByCondicionCampo(String condicionCampo);


}

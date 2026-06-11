package com.Reservas.SistemaReservas.Services.reglas;

import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.dto.request.CampoFutbolRequest;
import com.Reservas.SistemaReservas.dto.response.CampoFutbolResponse;


import java.util.List;

public interface CampoFutbolImpl {
    List<CampoFutbolResponse> listAll ();
    CampoFutbolResponse save(CampoFutbolRequest campoFutbolRequest);
    CampoFutbol findById(Long id);
    List<CampoFutbolResponse> findByTamanoCampo(String tamanoCampo);

    List<CampoFutbolResponse> findBySuperficieCampo(String superficieCampo);

    List<CampoFutbolResponse> findByCondicionCampo(String condicionCampo);


}

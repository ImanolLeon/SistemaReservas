package com.Reservas.SistemaReservas.Services.impl;

import com.Reservas.SistemaReservas.Entity.Camiseta;
import com.Reservas.SistemaReservas.Repository.CamisetaRepository;
import com.Reservas.SistemaReservas.Services.reglas.CamisetaServiceImpl;
import com.Reservas.SistemaReservas.dto.request.CamisetaDto;
import com.Reservas.SistemaReservas.excepcion.ApiExcepcion;
import com.Reservas.SistemaReservas.excepcion.MensajesExcepction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CamisetaService implements CamisetaServiceImpl {
    @Autowired
    private CamisetaRepository camisetaRepository;

    @Override
    public CamisetaDto save(CamisetaDto camisetaDto) {
         if(validarCamiseta(camisetaDto)){
        Camiseta camiseta = Camiseta.builder()
                .color(camisetaDto.color())
                .rutaImagen(camisetaDto.rutaImagen())
                .reservaCamiseta(camisetaDto.reservaCamisetas())
                .build();

        camisetaRepository.save(camiseta);
         return camisetaDto;
         }
         return null;
    }

    @Override
    public Camiseta findById(Long id) {

        if (id<0){
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted(id));
        }else{
           Camiseta camiseta= camisetaRepository.findById(id).orElseThrow(
                   () -> ApiExcepcion.valueInvalid(MensajesExcepction.VALOR_NO_ENCONTRADO.formatted(id))
           );
           return camiseta;
        }
    }


    @Override
    public List<CamisetaDto> findAll() {
        return camisetaRepository.findAll().stream().map(
                camiseta -> new CamisetaDto(
                        camiseta.getColor(),
                        camiseta.getRutaImagen(),
                        camiseta.getReservaCamiseta())
        ).toList();
    }

    @Override
    public boolean validarCamiseta(CamisetaDto camisetaDto) {
        if (camisetaDto.color().isBlank()){
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted("COLOR_CAMISETA"));
        };

        if (camisetaDto.rutaImagen().isBlank()){
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted("RUTA_IMAGEN"));
        }

        return true;
    }
}

package com.Reservas.SistemaReservas.Services.impl;

import com.Reservas.SistemaReservas.Entity.Balon;
import com.Reservas.SistemaReservas.Entity.Enum.TamanoBalon;
import com.Reservas.SistemaReservas.Repository.BalonRepository;
import com.Reservas.SistemaReservas.Services.reglas.BalonServiceImpl;
import com.Reservas.SistemaReservas.dto.request.BalonDto;
import com.Reservas.SistemaReservas.excepcion.ApiExcepcion;
import com.Reservas.SistemaReservas.excepcion.MensajesExcepction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BalonService implements BalonServiceImpl {

    @Autowired
    private BalonRepository balonRepository;

    @Override
    public BalonDto save(BalonDto balon) {

        boolean validacion = validarBalon(balon);
        if (validacion) {

            Balon balon1 = Balon.builder()
                    .balon(TamanoBalon.valueOf(balon.tamanoBalon().toUpperCase()))
                    .rutaImagen(balon.rutaImagen())
                    .build();

            balonRepository.save(balon1);
            return balon;
        } else {
            return null;
        }
    }

    @Override
    public Balon findById(Long id) {
        if (id < 0 || id == null) {
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted(id));
        }

        Balon balon = balonRepository.findById(id).orElseThrow(
                () -> ApiExcepcion.userNotFound(MensajesExcepction.USUARIO_NO_ENCONTRADO.formatted(id))
        );

        return balon;
    }



    @Override
    public List<BalonDto> listAll() {

        List<Balon> balon= balonRepository.findAll();

        return balon.stream()
                .map(balons -> new BalonDto(
                        balons.getBalon().name(),
                        balons.getRutaImagen(),
                        balons .getReservasBalones())
                )
                .toList();
    }

    //buscar por el tamaño de balón
    @Override
    public List<BalonDto> findByTamanoBalon(String balon) {
        if(balon.isBlank()){
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted(balon));
        }

        boolean existe = Arrays.stream(TamanoBalon.values())
                .anyMatch(t -> t.name().equals(balon.toUpperCase()));

        if(existe){

            List<BalonDto> balonD = balonRepository.findByBalon(TamanoBalon.valueOf(balon.toUpperCase())).stream()
                    .map(balonEntidad -> new BalonDto(
                            balonEntidad.getBalon().name(),
                            balonEntidad.getRutaImagen(),
                            balonEntidad.getReservasBalones()
                    )).toList();

            return  balonD;
        }else {
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted("Tamaño balón"));
        }

    }


    @Override
    public boolean validarBalon(BalonDto balon) {
       if( balon.rutaImagen().isBlank()){
           throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted("IMAGEN_INCORRECTA"));
       }

       if(balon.tamanoBalon().isBlank()){
           throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted("ESPACIO_EN_BLANCO"));
       }

       boolean existe = Arrays.stream(TamanoBalon.values())
               .anyMatch(t -> t.name().equals(balon.tamanoBalon().toUpperCase()));

      if(existe){
          return true;
      }else {
          throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted("BALON_ERRONEO"));
      }

    }
}

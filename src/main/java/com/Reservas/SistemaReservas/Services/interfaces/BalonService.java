package com.Reservas.SistemaReservas.Services.interfaces;

import com.Reservas.SistemaReservas.Entity.Balon;
import com.Reservas.SistemaReservas.Entity.Enum.TamanoBalon;
import com.Reservas.SistemaReservas.Repository.BalonRepository;
import com.Reservas.SistemaReservas.Services.implementation.BalonServiceImpl;
import com.Reservas.SistemaReservas.dto.request.BalonDto;
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
            throw new RuntimeException("id incorrecto");
        }

        Balon balon = balonRepository.findById(id).orElseThrow(
                () -> new RuntimeException("id no encontrado" +
                        " en la base de datos")
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
            throw new RuntimeException("el tamaño del balón tiene espacio en blanco");
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
            throw  new RuntimeException("Tamaño de balon incorrecto");
        }

    }


    @Override
    public boolean validarBalon(BalonDto balon) {
       if( balon.rutaImagen().isBlank()){
           throw new RuntimeException("La ruta de la imagen es incorrecta");
       }

       if(balon.tamanoBalon().isBlank()){
           throw new RuntimeException("el tamaño del balón tiene espacio en blanco");
       }

       boolean existe = Arrays.stream(TamanoBalon.values())
               .anyMatch(t -> t.name().equals(balon.tamanoBalon().toUpperCase()));

      if(existe){
          return true;
      }else {
          throw new RuntimeException("tipo de balon erroneo");
      }

    }
}

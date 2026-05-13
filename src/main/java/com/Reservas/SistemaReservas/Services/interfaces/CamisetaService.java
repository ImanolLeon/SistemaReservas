package com.Reservas.SistemaReservas.Services.interfaces;

import com.Reservas.SistemaReservas.Entity.Camiseta;
import com.Reservas.SistemaReservas.Repository.CamisetaRepository;
import com.Reservas.SistemaReservas.Services.implementation.CamisetaServiceImpl;
import com.Reservas.SistemaReservas.dto.request.CamisetaDto;
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
            throw new RuntimeException("El id no puede ser negativo");
        }else{
           Camiseta camiseta= camisetaRepository.findById(id).orElseThrow(
                   () -> new RuntimeException("El numero no se encontró en la base de datos")
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
            throw  new RuntimeException("el color de la camiseta no puede ser en blanco");
        }

        if (camisetaDto.rutaImagen().isBlank()){
            throw new RuntimeException("La ruta no puede estar vacia");
        }

        return true;
    }
}

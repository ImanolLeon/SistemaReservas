package com.Reservas.SistemaReservas.Services.interfaces;

import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.Entity.Enum.CondicionCampo;
import com.Reservas.SistemaReservas.Entity.Enum.SuperficieCampo;
import com.Reservas.SistemaReservas.Entity.Enum.TamanoCampo;
import com.Reservas.SistemaReservas.Repository.CampoFutbolRepository;
import com.Reservas.SistemaReservas.Services.implementation.CampoFutbolImpl;
import com.Reservas.SistemaReservas.dto.request.CampoFutbolRequest;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CampoFutbolService implements CampoFutbolImpl {
    @Autowired
    private CampoFutbolRepository campoFutbolRepository;

    @Override
    public List<CampoFutbolRequest> listAll() {

        return campoFutbolRepository.findAll().stream()
                .map(campoFutbolEntity -> new CampoFutbolRequest(
                        campoFutbolEntity.getNombre(),
                        campoFutbolEntity.getCondicionCampo().name(),
                        campoFutbolEntity.getTamanoCampo().name(),
                        campoFutbolEntity.getSuperficieCampo().name(),
                        campoFutbolEntity.getReserva()
                )).toList();
    }

    @Override
    public CampoFutbolRequest save(CampoFutbolRequest campoFutbolRequest) {
        if (validarCampoFutbol(campoFutbolRequest)){
            campoFutbolRepository.save(convertirRequestEntity(campoFutbolRequest));
        }else{
            throw new RuntimeException("Datos incorrectos");
        }
        return campoFutbolRequest;
    }

    @Override
    public CampoFutbol findById(Long id) {
        if(id<=0){
            throw  new RuntimeException("Numero menor a cero");
        }
        return campoFutbolRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("campo de futbol no encontrado en la base de datos")
        );
    }

    public List<CampoFutbol> listarTodo(){
        return  campoFutbolRepository.findAll();
    }

    public boolean validarString(String valorEnum,Class<? extends Enum<?>> enumClass){
       return Arrays.stream(enumClass.getEnumConstants()).anyMatch(
                tamanoCampoEnum -> tamanoCampoEnum.name().equals(valorEnum.toUpperCase())
        );
    }

    @Override
    public List<CampoFutbolRequest> findByTamanoCampo(String tamanoCampo) {

        if(validarString(tamanoCampo,TamanoCampo.class)){
            return campoFutbolRepository.findByTamanoCampo(TamanoCampo.valueOf(tamanoCampo.toUpperCase())).stream().map(
                    campoEntity -> new CampoFutbolRequest(
                            campoEntity.getNombre(),
                            campoEntity.getCondicionCampo().name(),
                            campoEntity.getTamanoCampo().name(),
                            campoEntity.getSuperficieCampo().name(),
                            campoEntity.getReserva()
                    )
            ).toList();
        }else {
            throw new RuntimeException("Nos se encontró ese tamaño de campo");
        }

    }

    @Override
    public List<CampoFutbolRequest> findBySuperficieCampo(String superficieCampo) {
        if(validarString(superficieCampo, SuperficieCampo.class)){
            return campoFutbolRepository.findBySuperficieCampo(SuperficieCampo.valueOf(superficieCampo.toUpperCase())).stream().map(
                    campoEntity -> new CampoFutbolRequest(
                            campoEntity.getNombre(),
                            campoEntity.getCondicionCampo().name(),
                            campoEntity.getTamanoCampo().name(),
                            campoEntity.getSuperficieCampo().name(),
                            campoEntity.getReserva()
                    )
            ).toList();
        }else {
            throw new RuntimeException("No se encontró dicha superficie en" +
                    " la base de datos");
        }
    }

    @Override
    public List<CampoFutbolRequest> findByCondicionCampo(String condicionCampo) {
        if(!validarString(condicionCampo, CondicionCampo.class)){
            throw new RuntimeException("Condicion no encontrada");
        }
        return campoFutbolRepository.findByCondicionCampo(CondicionCampo.valueOf(condicionCampo.toUpperCase())).stream().map(
                campoEntity -> new CampoFutbolRequest(
                        campoEntity.getNombre(),
                        campoEntity.getCondicionCampo().name(),
                        campoEntity.getTamanoCampo().name(),
                        campoEntity.getSuperficieCampo().name(),
                        campoEntity.getReserva()
                )
        ).toList();
    }

    public CampoFutbol convertirRequestEntity(CampoFutbolRequest campoFutbolRequest){
        return CampoFutbol.builder()
                .nombre(campoFutbolRequest.nombre())
                .condicionCampo(CondicionCampo.valueOf(campoFutbolRequest.condicionCampo()))
                .superficieCampo(SuperficieCampo.valueOf(campoFutbolRequest.superficie()))
                .tamanoCampo(TamanoCampo.valueOf(campoFutbolRequest.tamanoCampo()))
                .reserva(campoFutbolRequest.reservas())
                .build();
    }

    public boolean validarCampoFutbol(CampoFutbolRequest campoFutbolRequest){
        boolean condicionCampoValidado= Arrays.stream(CondicionCampo.values())
                .anyMatch(condicionCampoEnum -> condicionCampoEnum.name()
                        .equals(campoFutbolRequest.condicionCampo()));

        boolean supercifieCampo = Arrays.stream(SuperficieCampo.values()).anyMatch(
                superficieCampoEntity -> superficieCampoEntity.name().equals(campoFutbolRequest.superficie())
        );

        boolean tamanoCampo = Arrays.stream(TamanoCampo.values()).anyMatch(
                tamanoCampoEnum -> tamanoCampoEnum.name().equals(campoFutbolRequest.tamanoCampo())
        );
        return tamanoCampo && supercifieCampo && condicionCampoValidado;

    }

}

package com.Reservas.SistemaReservas.Services.impl;

import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.Entity.Enum.CondicionCampo;
import com.Reservas.SistemaReservas.Entity.Enum.SuperficieCampo;
import com.Reservas.SistemaReservas.Entity.Enum.TamanoCampo;
import com.Reservas.SistemaReservas.Repository.CampoFutbolRepository;
import com.Reservas.SistemaReservas.Services.reglas.CampoFutbolImpl;
import com.Reservas.SistemaReservas.dto.Mapper.ReservaMapper;
import com.Reservas.SistemaReservas.dto.request.CampoFutbolRequest;
import com.Reservas.SistemaReservas.dto.response.CampoFutbolResponse;
import com.Reservas.SistemaReservas.excepcion.ApiExcepcion;
import com.Reservas.SistemaReservas.excepcion.MensajesExcepction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CampoFutbolService implements CampoFutbolImpl {

    private CampoFutbolRepository campoFutbolRepository;
    private ReservaService reservaService;
    private ReservaMapper reservaMapper;

    @Override
    public List<CampoFutbolResponse> listAll() {

        return campoFutbolRepository.findAll().stream()
                .map(campoFutbolEntity -> new CampoFutbolResponse(
                        campoFutbolEntity.getNombre(),
                        campoFutbolEntity.getCondicionCampo().name(),
                        campoFutbolEntity.getTamanoCampo().name(),
                        campoFutbolEntity.getSuperficieCampo().name(),
                        reservaService.findByCampoFutbol(campoFutbolEntity.getId())

                )).toList();
    }

    @Override
    public CampoFutbolResponse save(CampoFutbolRequest campoFutbolRequest) {
        if (validarCampoFutbol(campoFutbolRequest)){
            campoFutbolRepository.save(convertirRequestEntity(campoFutbolRequest));
        }else{
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted("CAMPO_FUTBOL"));
        }


        return CampoFutbolResponse.builder()
                .superficie(campoFutbolRequest.superficie())
                .condicionCampo(campoFutbolRequest.condicionCampo())
                .nombre(campoFutbolRequest.nombre())
                .tamanoCampo(campoFutbolRequest.tamanoCampo())
                .build();
    }

    @Override
    public CampoFutbol findById(Long id) {
        if(id<=0){
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted("MENOR_A_CERO"));
        }
        return campoFutbolRepository.findById(id).orElseThrow(

                ()->    ApiExcepcion.valueInvalid(MensajesExcepction.VALOR_NO_ENCONTRADO.formatted(id))
        );
    }

    public List<CampoFutbol> listarTodo(){
        return  campoFutbolRepository.findAll();
    }


    private boolean validarString(String valorEnum,Class<   ? extends Enum<?>  > enumClass){
       return Arrays.stream(enumClass.getEnumConstants()).anyMatch(
                tamanoCampoEnum -> tamanoCampoEnum.name().equals(valorEnum.toUpperCase())
        );
    }

    private CampoFutbol convertirRequestEntity(CampoFutbolRequest campoFutbolRequest){
        if(campoFutbolRequest.reservas()!=null){
            CampoFutbol campoFutbol = CampoFutbol.builder()
                    .nombre(campoFutbolRequest.nombre())
                    .reserva(campoFutbolRequest.reservas().stream().map(
                            reservaRequest -> reservaMapper.convertirReservaRequestToReserva(reservaRequest)
                    ).toList())
                    .superficieCampo(SuperficieCampo.valueOf(campoFutbolRequest.superficie()))
                    .condicionCampo(CondicionCampo.valueOf(campoFutbolRequest.condicionCampo()))
                    .tamanoCampo(TamanoCampo.valueOf(campoFutbolRequest.tamanoCampo()))
                    .build();
            return campoFutbol;
        }else{
            CampoFutbol campoFutbol = CampoFutbol.builder()
                    .nombre(campoFutbolRequest.nombre())
                    .reserva(null)
                    .superficieCampo(SuperficieCampo.valueOf(campoFutbolRequest.superficie()))
                    .condicionCampo(CondicionCampo.valueOf(campoFutbolRequest.condicionCampo()))
                    .tamanoCampo(TamanoCampo.valueOf(campoFutbolRequest.tamanoCampo()))
                    .build();
            return campoFutbol;
        }

    }

    @Override
    public List<CampoFutbolResponse> findByTamanoCampo(String tamanoCampo) {

        if(validarString(tamanoCampo,TamanoCampo.class)){
            return campoFutbolRepository.findByTamanoCampo(TamanoCampo.valueOf(tamanoCampo.toUpperCase())).stream().map(
                    campoEntity -> new CampoFutbolResponse(
                            campoEntity.getNombre(),
                            campoEntity.getCondicionCampo().name(),
                            campoEntity.getTamanoCampo().name(),
                            campoEntity.getSuperficieCampo().name(),
                            reservaService.findByCampoFutbol(campoEntity.getId())
                    )
            ).toList();
        }else {
            throw ApiExcepcion.valueInvalid(MensajesExcepction.VALOR_NO_ENCONTRADO.formatted("TAMAÑO_CAMPO"));
        }

    }

    @Override
    public List<CampoFutbolResponse> findBySuperficieCampo(String superficieCampo) {
        if(validarString(superficieCampo, SuperficieCampo.class)){
            return campoFutbolRepository.findBySuperficieCampo(SuperficieCampo.valueOf(superficieCampo.toUpperCase())).stream().map(
                    campoEntity -> new CampoFutbolResponse(
                            campoEntity.getNombre(),
                            campoEntity.getCondicionCampo().name(),
                            campoEntity.getTamanoCampo().name(),
                            campoEntity.getSuperficieCampo().name(),
                            reservaService.findByCampoFutbol(campoEntity.getId())
                    )
            ).toList();
        }else {
            throw ApiExcepcion.valueInvalid(MensajesExcepction.USUARIO_NO_ENCONTRADO.formatted("SUPERCIE"));
        }
    }

    @Override
    public List<CampoFutbolResponse> findByCondicionCampo(String condicionCampo) {
        if(!validarString(condicionCampo, CondicionCampo.class)){
            throw ApiExcepcion.valueInvalid(MensajesExcepction.VALOR_NO_ENCONTRADO.formatted("CONDICIÓN_CAMPO"));
        }
        return campoFutbolRepository.findByCondicionCampo(CondicionCampo.valueOf(condicionCampo.toUpperCase())).stream().map(
                campoEntity -> new CampoFutbolResponse(
                        campoEntity.getNombre(),
                        campoEntity.getCondicionCampo().name(),
                        campoEntity.getTamanoCampo().name(),
                        campoEntity.getSuperficieCampo().name(),
                        reservaService.findByCampoFutbol(campoEntity.getId())
                )
        ).toList();
    }


    private boolean validarCampoFutbol(CampoFutbolRequest campoFutbolRequest){
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

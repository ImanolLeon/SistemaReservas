package com.Reservas.SistemaReservas.Services.impl;

import com.Reservas.SistemaReservas.Entity.Balon;
import com.Reservas.SistemaReservas.Entity.Reserva;
import com.Reservas.SistemaReservas.Entity.ReservaBalon;
import com.Reservas.SistemaReservas.Repository.ReservaBalonRepository;
import com.Reservas.SistemaReservas.Services.reglas.ReservaBalonImpl;
import com.Reservas.SistemaReservas.dto.response.ReservaBalonResponse;
import com.Reservas.SistemaReservas.excepcion.ApiExcepcion;
import com.Reservas.SistemaReservas.excepcion.MensajesExcepction;
import com.Reservas.SistemaReservas.validations.interfaz.IdValidationI;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalTime;
import java.util.List;

@Validated
@AllArgsConstructor
@Service
public class ReservaBalonService implements ReservaBalonImpl {
    private ReservaBalonRepository reservaBalonRepository;
    private ReservaService reservaService;
    private BalonService balonService;



    private Long verificarCruce(Long id , LocalTime horaInicio, LocalTime horaFinal,String dia){
        return reservaBalonRepository.findByIdAndHoraInicio(id,horaFinal,horaInicio,dia);
    }

    @Override
    public ReservaBalonResponse save(@Min(1) Long idReserva
            , @Min(1) Long idBalon) {

        Reserva reserva = reservaService.buscarPorId(idReserva);
        Balon balon = balonService.findById(idBalon);

        ReservaBalon reservaBalon = ReservaBalon.builder()
                .idreserva(reserva)
                .idBalon(balon)
                .build();

        Long cruce =verificarCruce(balon.getIdBalon(),reserva.getHoraFinal(),reserva.getHoraInicio(),reserva.getDia());

        if (cruce>0){
            //Excepcion que valide cruce...
            throw  ApiExcepcion.valueInvalid(MensajesExcepction.CRUCE_HORARIO.formatted("hora inicio,hora final"));
        }else{
            reservaBalonRepository.save(reservaBalon);
            return new ReservaBalonResponse(
                    reserva.getHoraInicio().toString(),
                    reserva.getHoraFinal().toString(),
                    "Balon reservado con exito"
            );
        }

    }


    @Override
    public ReservaBalon findById(@IdValidationI Long id) {

        return reservaBalonRepository.findById(id).orElseThrow(
                () -> ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted(id))
        );
    }

    @Override
    public List<ReservaBalonResponse> listAll() {

        return reservaBalonRepository.findAll().stream().map(
                entidad -> new ReservaBalonResponse(
                        entidad.getIdreserva().getHoraInicio().toString(),
                        entidad.getIdBalon().getIdBalon().toString(),
                        "Reservado"
                )
        ).toList();
    }
}

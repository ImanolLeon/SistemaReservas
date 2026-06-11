package com.Reservas.SistemaReservas.dto.Mapper;

import com.Reservas.SistemaReservas.Entity.Enum.EstadoReserva;
import com.Reservas.SistemaReservas.Entity.Reserva;
import com.Reservas.SistemaReservas.Entity.security.Usuario;
import com.Reservas.SistemaReservas.Repository.UsuarioRepository;
import com.Reservas.SistemaReservas.dto.request.ReservaRequest;
import com.Reservas.SistemaReservas.dto.response.ReservaReponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
@Component
@AllArgsConstructor
public class ReservaMapper {

    private UsuarioRepository usuarioRepository;



    public  Reserva convertirReservaRequestToReserva(ReservaRequest reservaRequest) {
       Usuario usuario = usuarioRepository.findById(reservaRequest.idUsuario()).orElseThrow(
               () -> new RuntimeException("Usuario no encontrado")
       );

       Reserva reserva = Reserva.builder()
               .estadoReserva(EstadoReserva.RESERVADA)
               .dia(reservaRequest.dia())
               .horaFinal(LocalTime.parse(reservaRequest.horaFinal()))
               .horaInicio(LocalTime.parse(reservaRequest.horaInicio()))
               .precio(reservaRequest.precio())
               .fecha(reservaRequest.fecha())
               .build();
        return reserva;
    }


    public ReservaReponse convertirReservaRequestToReservaResponse(ReservaRequest reservaRequest){
        Usuario usuario = usuarioRepository.findById(reservaRequest.idUsuario()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );

        return  ReservaReponse.builder()
                .nombreUsuario(usuario.getNombre())
                .dia(reservaRequest.dia())
                .fecha(reservaRequest.fecha())
                .horaInicio(reservaRequest.horaInicio())
                .horaFinal(reservaRequest.horaFinal())
                .build();
    }
}

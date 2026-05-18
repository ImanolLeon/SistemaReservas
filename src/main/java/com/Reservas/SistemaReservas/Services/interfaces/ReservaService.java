package com.Reservas.SistemaReservas.Services.interfaces;

import com.Reservas.SistemaReservas.dto.request.ReservaRequest;
import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.Entity.Enum.EstadoReserva;
import com.Reservas.SistemaReservas.Entity.Reserva;
import com.Reservas.SistemaReservas.Entity.security.Usuario;
import com.Reservas.SistemaReservas.Repository.CampoFutbolRepository;
import com.Reservas.SistemaReservas.Repository.ReservaRepository;
import com.Reservas.SistemaReservas.Repository.UsuarioRepository;
import com.Reservas.SistemaReservas.Services.implementation.ReservaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservaService implements ReservaServiceImpl {

    private UsuarioRepository usuarioRepository;
    private ReservaRepository reservaRepository;
    private CampoFutbolRepository campoFutbolRepository;


    @Override
    public boolean validarReserva(Reserva reserva) {
        return true;
    }

    @Override
    public ReservaRequest cambiarEstado(Long id) {

        if(id<=0){
            throw new RuntimeException("el id es menor o igual que cero");
        }
        Reserva reserva = reservaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontró una reserva con ese id")
        );

        return null;
    }

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> findByDia(String dia) {
        //validar si está vacio
        if(dia.isEmpty()){
            throw  new RuntimeException("elemento vacio");
        }
        //impedir que coloque un dia que no es de la semana
        List<String> diasValidos = List.of(
                "LUNES", "MARTES", "MIERCOLES",
                "JUEVES", "VIERNES", "SABADO", "DOMINGO"
        );

        if(!diasValidos.contains(dia.toUpperCase())){
            throw new RuntimeException("dia no valido");
        }

        List<Reserva> reservas = reservaRepository.findByDia(dia);
        //validar si la lista está vacia
        if(reservas.isEmpty()){
            throw new RuntimeException("No se encontraron reservas para el dia" + dia.toUpperCase());
        }
        return reservaRepository.findByDia(dia);
    }

    @Override
    public List<Reserva> findByEstadoReserva(EstadoReserva estado) {
            if(estado==null){
                throw  new RuntimeException(" el estado de la reserva es nulo");
            }
        return reservaRepository.findByEstadoReserva(estado);
    }

    //ValidarReserva


    @Override
    public ReservaRequest guardar(ReservaRequest reserva) {
        //validacion
        Usuario usuario =  usuarioRepository.findById(reserva.idUsuario()).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado"));

        CampoFutbol campoFutbol = campoFutbolRepository.findById(reserva.campoFutbol()).
                orElseThrow( () -> new RuntimeException("No se encontró campod e futbol"));

        Reserva reserva1= Reserva.builder()
                .idCampoFutbol(campoFutbol)
                .fecha(reserva.fecha())
                .horaInicio(LocalTime.parse(reserva.horaInicio()))
                .horaFinal(LocalTime.parse(reserva.horaFinal()))
                .balones(null)
                .camisetas(null)
                .precio(reserva.precio())
                .usuario(usuario)
                .dia(reserva.dia())
                .build();
        return  reserva;

    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) {
        return Optional.empty();
    }




}

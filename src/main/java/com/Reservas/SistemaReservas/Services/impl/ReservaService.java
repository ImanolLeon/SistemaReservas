package com.Reservas.SistemaReservas.Services.impl;

import com.Reservas.SistemaReservas.Entity.Balon;
import com.Reservas.SistemaReservas.Entity.ReservaBalon;
import com.Reservas.SistemaReservas.Repository.BalonRepository;
import com.Reservas.SistemaReservas.dto.request.ReservaRequest;
import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.Entity.Enum.EstadoReserva;
import com.Reservas.SistemaReservas.Entity.Reserva;
import com.Reservas.SistemaReservas.Entity.security.Usuario;
import com.Reservas.SistemaReservas.Repository.CampoFutbolRepository;
import com.Reservas.SistemaReservas.Repository.ReservaRepository;
import com.Reservas.SistemaReservas.Repository.UsuarioRepository;
import com.Reservas.SistemaReservas.Services.reglas.ReservaServiceImpl;
import com.Reservas.SistemaReservas.dto.response.ReservaReponse;
import com.Reservas.SistemaReservas.excepcion.ApiExcepcion;
import com.Reservas.SistemaReservas.excepcion.MensajesExcepction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservaService implements ReservaServiceImpl {

    private UsuarioRepository usuarioRepository;
    private ReservaRepository reservaRepository;
    private CampoFutbolRepository campoFutbolRepository;
    private BalonRepository balonRepository;
    private BalonService balonService;


    @Override
    public boolean cancelarReserva(Long id) {

        if(id<=0){
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted(id));
        }
        Reserva reserva = reservaRepository.findById(id).orElseThrow(
                () ->   ApiExcepcion.valueInvalid(MensajesExcepction.VALOR_NO_ENCONTRADO.formatted(id))
        );

        reserva.setEstadoReserva(EstadoReserva.DISPONIBLE);

        return true;
    }

    @Override
    public List<ReservaReponse> findByCampoFutbol(Long id) {
        if(id<=0){
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted(id));

        }

        CampoFutbol campoFutbol = campoFutbolRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Campo no encontrado con ese Id")
        );

        return reservaRepository.findByIdCampoFutbol(campoFutbol).stream().map(
                entidad -> new ReservaReponse(
                        entidad.getUsuario().getNombre(),
                        entidad.getHoraInicio().toString(),
                        entidad.getHoraFinal().toString(),
                        entidad.getFecha(),
                        entidad.getDia()
                )
        ).toList();

    }

    @Override
    public List<ReservaReponse> findByHoraInicio(LocalTime horaInicio) {

        if (horaInicio.toString().isBlank() ){
            throw  ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted(horaInicio));
        }

      List<Reserva> reservasPorHora = reservaRepository.findByHoraInicio(horaInicio);

              return reservasPorHora.stream()
                 .map(entidad -> new ReservaReponse(
                         entidad.getUsuario().getNombre(),
                         entidad.getHoraInicio().toString(),
                         entidad.getHoraFinal().toString(),
                         entidad.getFecha(),
                         entidad.getDia()
                 )).toList() ;

    }



    @Override
    public List<ReservaReponse> findAll() {

        return reservaRepository.findAll().stream().map(
                entidad -> new ReservaReponse(
                        entidad.getUsuario().getNombre(),
                        entidad.getHoraInicio().toString(),
                        entidad.getHoraFinal().toString(),
                        entidad.getFecha(),
                        entidad.getDia()
                )
        ).toList();
    }

    @Override
    public List<ReservaReponse> findByDia(String dia) {
        //validar si está vacio
        if(dia.isBlank()){
            throw  new RuntimeException("elemento en blanco");
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

        return reservaRepository.findByDia(dia).stream().map(
                reserva -> new ReservaReponse(
                        reserva.getUsuario().getNombre(),
                        reserva.getHoraInicio().toString(),
                        reserva.getHoraFinal().toString(),
                        reserva.getFecha(),
                        reserva.getDia()
                )
        ).toList();
    }


    private boolean validarEstadoReserva(String estado){

        return Arrays.stream(EstadoReserva.values()).anyMatch(
                estadoReserva -> estadoReserva.name().equals(estado.toUpperCase())
        );
    }

    @Override
    public List<ReservaReponse> findByEstadoReserva(String estado) {
        if(estado.isBlank()){
            throw new RuntimeException("El estado es nulo");
        }

        if(!validarEstadoReserva(estado)){
            throw new RuntimeException("es estado no existe");
        }


        return reservaRepository.findByEstadoReserva(EstadoReserva.valueOf(estado)).stream(
        ).map(
                entidad -> new ReservaReponse(
                        entidad.getUsuario().getNombre(),
                        entidad.getHoraInicio().toString(),
                        entidad.getHoraFinal().toString(),
                        entidad.getFecha(),
                        entidad.getDia()
                )
        ).toList();
    }


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
                .estadoReserva(EstadoReserva.RESERVADA)
                .build();

        List<Reserva> colapsadas= reservaRepository.findReservaColapsadas(campoFutbol.getId(),reserva.fecha(), reserva1.getDia(),
                LocalTime.parse(reserva.horaFinal()),LocalTime.parse(reserva.horaInicio()));
        //validar que nos e cruce

        if(!colapsadas.isEmpty()){
            throw new RuntimeException("Hay cruce");
        }

        reservaRepository.save(reserva1);
        return  reserva;

    }

    @Override
    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id).orElseThrow(
                ()->    ApiExcepcion.valueInvalid(MensajesExcepction.VALOR_NO_ENCONTRADO.formatted(id))
        );
    }




}

package com.Reservas.SistemaReservas.Services.impl;

import com.Reservas.SistemaReservas.Entity.Balon;
import com.Reservas.SistemaReservas.Entity.Camiseta;
import com.Reservas.SistemaReservas.Entity.ReservaBalon;
import com.Reservas.SistemaReservas.Entity.ReservaCamiseta;
import com.Reservas.SistemaReservas.Repository.*;
import com.Reservas.SistemaReservas.Repository.Especificaciones.ReservaEspecificaciones;
import com.Reservas.SistemaReservas.dto.request.ReservaBalonRequest;
import com.Reservas.SistemaReservas.dto.request.ReservaRequest;
import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.Entity.Enum.EstadoReserva;
import com.Reservas.SistemaReservas.Entity.Reserva;
import com.Reservas.SistemaReservas.Entity.security.Usuario;
import com.Reservas.SistemaReservas.Services.reglas.ReservaServiceImpl;
import com.Reservas.SistemaReservas.dto.request.ReservaRequestEspecification;
import com.Reservas.SistemaReservas.dto.response.ReservaBalonResponse;
import com.Reservas.SistemaReservas.dto.response.ReservaCamisetaResponse;
import com.Reservas.SistemaReservas.dto.response.ReservaReponse;
import com.Reservas.SistemaReservas.excepcion.ApiExcepcion;
import com.Reservas.SistemaReservas.excepcion.MensajesExcepction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservaService implements ReservaServiceImpl {

    private UsuarioRepository usuarioRepository;
    private ReservaRepository reservaRepository;
    private CampoFutbolRepository campoFutbolRepository;
    private BalonService balonService;
    private CamisetaService camisetaService;
    private ReservaBalonRepository reservaBalonRepository;
    private ReservaCamisetaRepository reservaCamisetaRepository;

    private static final List<String> DIAS_VALIDOS =
            List.of("LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO");

    @Override
    public boolean cancelarReserva(Long id) {

        if (id <= 0) {
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted(id));
        }
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> ApiExcepcion.valueInvalid(MensajesExcepction.VALOR_NO_ENCONTRADO.formatted(id)));

        if (reserva.getEstadoReserva() == EstadoReserva.DISPONIBLE) {
            throw ApiExcepcion.valueInvalid("La reserva %s ya estaba cancelada".formatted(id));
        }

        reserva.setEstadoReserva(EstadoReserva.DISPONIBLE);
        // FIX: sin este save el cambio de estado nunca se persistia en la BD
        reservaRepository.save(reserva);

        return true;
    }

    @Override
    public List<ReservaReponse> findByCampoFutbol(Long id) {
        if (id <= 0) {
            throw ApiExcepcion.valueInvalid(MensajesExcepction.DATO_INVALIDO.formatted(id));
        }

        CampoFutbol campoFutbol = campoFutbolRepository.findById(id)
                .orElseThrow(() -> ApiExcepcion.userNotFound("Campo de futbol no encontrado con ese Id"));

        return reservaRepository.findByIdCampoFutbol(campoFutbol).stream()
                .map(ReservaService::toResponse)
                .toList();
    }

    @Override
    public List<ReservaReponse> findByHoraInicio(LocalTime horaInicio) {
        return reservaRepository.findByHoraInicio(horaInicio).stream()
                .map(ReservaService::toResponse)
                .toList();
    }

    @Override
    public ReservaBalonResponse guardarReservaBalon(Long idReserva, Long idBalon) {
        Reserva reserva = buscarPorId(idReserva);
        Balon balon = balonService.findById(idBalon);

        long cruce = reservaRepository.contarCrucesReservaBalon(
                idBalon,
                reserva.getHoraFinal(),
                reserva.getHoraInicio(),
                reserva.getDia(),
                reserva.getFecha()
        );
        if (cruce > 0) {
            throw ApiExcepcion.valueInvalid(MensajesExcepction.CRUCE_HORARIO.formatted("id reserva:" + idReserva));
        }

        ReservaBalon reservaBalon = reservaBalonRepository.save(
                ReservaBalon.builder()
                        .idBalon(balon)
                        .idreserva(reserva)
                        .build());
        reserva.setBalones(reservaBalon);
        reservaRepository.save(reserva);

        return new ReservaBalonResponse(reserva.getHoraInicio().toString(), reserva.getHoraFinal().toString(),
                "Reservado con éxito");
    }

    @Override
    public void eliminarReservaBalon(Long idReserva) {
        long cruce = reservaRepository.buscarReservaBalonEnReserva(idReserva);
        if (cruce > 0) {
            Reserva reserva = buscarPorId(idReserva);
            reserva.setBalones(null);
            reservaRepository.save(reserva);
        } else {
            throw ApiExcepcion.valueInvalid(MensajesExcepction.VALOR_NO_ENCONTRADO.formatted("No se tiene reserva de balón"));
        }
    }

    @Override
    public ReservaCamisetaResponse reservarCamiseta(Long idReserva, Long idCamiseta) {
        Reserva reserva = buscarPorId(idReserva);
        Camiseta camiseta = camisetaService.findById(idCamiseta);

        long cruce = reservaRepository.findCruceReservaCamiseta(
                camiseta.getId(),
                reserva.getHoraFinal(),
                reserva.getHoraInicio(),
                reserva.getDia(),
                reserva.getFecha());

        if (cruce > 0) {
            throw ApiExcepcion.valueInvalid(MensajesExcepction.CRUCE_HORARIO.formatted("id reserva:" + idReserva));
        }

        ReservaCamiseta reservaCamiseta = reservaCamisetaRepository.save(
                ReservaCamiseta.builder()
                        .camisetaId(camiseta)
                        .build());

        reserva.setCamisetas(reservaCamiseta);
        reservaRepository.save(reserva);

        return new ReservaCamisetaResponse(
                reserva.getHoraInicio().toString(),
                reserva.getHoraFinal().toString(),
                "Camiseta reservada con éxito");
    }

    @Override
    public void eliminarReservaCamiseta(Long idReserva) {
        long tieneCamiseta = reservaRepository.buscarReservaCamisetaEnReserva(idReserva);
        if (tieneCamiseta > 0) {
            Reserva reserva = buscarPorId(idReserva);
            reserva.setCamisetas(null);
            reservaRepository.save(reserva);
        } else {
            throw ApiExcepcion.valueInvalid(MensajesExcepction.VALOR_NO_ENCONTRADO.formatted("No se tiene reserva de camiseta"));
        }
    }

    @Override
    public List<ReservaReponse> listarPorParametros(ReservaRequestEspecification reservaRequestEspecification) {
        ReservaEspecificaciones reservaEspecificaciones =
                new ReservaEspecificaciones(reservaRequestEspecification);
        return reservaRepository.findAll(reservaEspecificaciones).stream()
                .map(ReservaService::toResponse)
                .toList();
    }

    @Override
    public List<ReservaReponse> findAll() {
        return reservaRepository.findAll().stream()
                .map(ReservaService::toResponse)
                .toList();
    }

    @Override
    public List<ReservaReponse> findByDia(String dia) {
        if (dia == null || dia.isBlank()) {
            throw ApiExcepcion.valueInvalid("El dia no puede estar en blanco");
        }

        if (!DIAS_VALIDOS.contains(dia.toUpperCase())) {
            throw ApiExcepcion.valueInvalid("El dia %s no es valido. Use: %s".formatted(dia, String.join(", ", DIAS_VALIDOS)));
        }

        // Antes lanzaba excepcion si no habia reservas; ahora devuelve lista vacia
        return reservaRepository.findByDia(dia.toUpperCase()).stream()
                .map(ReservaService::toResponse)
                .toList();
    }

    @Override
    public List<ReservaReponse> findByEstadoReserva(String estado) {
        if (estado == null || estado.isBlank()) {
            throw ApiExcepcion.valueInvalid("El estado no puede estar en blanco");
        }

        if (!validarEstadoReserva(estado)) {
            throw ApiExcepcion.valueInvalid("El estado %s no existe".formatted(estado));
        }

        return reservaRepository.findByEstadoReserva(EstadoReserva.valueOf(estado.toUpperCase())).stream()
                .map(ReservaService::toResponse)
                .toList();
    }

    private boolean validarEstadoReserva(String estado) {
        return Arrays.stream(EstadoReserva.values())
                .anyMatch(estadoReserva -> estadoReserva.name().equals(estado.toUpperCase()));
    }

    @Override
    public ReservaRequest guardar(ReservaRequest reserva) {
        Usuario usuario = usuarioRepository.findById(reserva.idUsuario())
                .orElseThrow(() -> ApiExcepcion.userNotFound(
                        MensajesExcepction.USUARIO_NO_ENCONTRADO.formatted(reserva.idUsuario())));

        CampoFutbol campoFutbol = campoFutbolRepository.findById(reserva.campoFutbol())
                .orElseThrow(() -> ApiExcepcion.userNotFound(
                        MensajesExcepction.VALOR_NO_ENCONTRADO.formatted("campo de futbol " + reserva.campoFutbol())));

        LocalTime horaInicio = parseHora(reserva.horaInicio());
        LocalTime horaFinal = parseHora(reserva.horaFinal());

        if (!horaFinal.isAfter(horaInicio)) {
            throw ApiExcepcion.valueInvalid("La hora final debe ser despues de la hora inicial");
        }

        if (reserva.fecha() == null) {
            throw ApiExcepcion.valueInvalid("La fecha es obligatoria");
        }

        String dia = reserva.dia() == null ? "" : reserva.dia().toUpperCase();
        if (!DIAS_VALIDOS.contains(dia)) {
            throw ApiExcepcion.valueInvalid("El dia %s no es valido. Use: %s".formatted(reserva.dia(), String.join(", ", DIAS_VALIDOS)));
        }

        Reserva reservaNueva = Reserva.builder()
                .idCampoFutbol(campoFutbol)
                .fecha(reserva.fecha())
                .horaInicio(horaInicio)
                .horaFinal(horaFinal)
                .balones(null)
                .camisetas(null)
                .precio(reserva.precio())
                .usuario(usuario)
                .dia(dia)
                .estadoReserva(EstadoReserva.RESERVADA)
                .build();

        List<Reserva> colapsadas = reservaRepository.findReservaColapsadas(
                campoFutbol.getId(), reserva.fecha(), dia, horaFinal, horaInicio);

        //validar que no se cruce
        if (!colapsadas.isEmpty()) {
            throw ApiExcepcion.valueInvalid(
                    MensajesExcepction.CRUCE_HORARIO.formatted("el campo %s ya esta ocupado de %s a %s"
                            .formatted(campoFutbol.getNombre(), reserva.horaInicio(), reserva.horaFinal())));
        }

        reservaRepository.save(reservaNueva);
        return reserva;
    }

    @Override
    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> ApiExcepcion.valueInvalid(MensajesExcepction.VALOR_NO_ENCONTRADO.formatted(id)));
    }

    @Override
    public ReservaReponse buscarPorIdResponse(Long id) {
        return toResponse(buscarPorId(id));
    }

    private LocalTime parseHora(String hora) {
        try {
            return LocalTime.parse(hora);
        } catch (DateTimeParseException e) {
            throw ApiExcepcion.valueInvalid("La hora '%s' es invalida. Use el formato HH:mm".formatted(hora));
        }
    }

    /**
     * Convierte la entidad Reserva a su respuesta, con todos los campos null-safe
     * (antes reventaba con NPE si la reserva no tenia balon asignado).
     */
    private static ReservaReponse toResponse(Reserva reserva) {
        return ReservaReponse.builder()
                .id(reserva.getId())
                .nombreUsuario(reserva.getUsuario() != null ? reserva.getUsuario().getNombre() : null)
                .nombreCampoFutbol(reserva.getIdCampoFutbol() != null ? reserva.getIdCampoFutbol().getNombre() : null)
                .horaInicio(reserva.getHoraInicio() != null ? reserva.getHoraInicio().toString() : null)
                .horaFinal(reserva.getHoraFinal() != null ? reserva.getHoraFinal().toString() : null)
                .fecha(reserva.getFecha())
                .dia(reserva.getDia())
                .precio(reserva.getPrecio())
                .estadoReserva(reserva.getEstadoReserva() != null ? reserva.getEstadoReserva().name() : null)
                .reservaBalones(
                        (reserva.getBalones() != null && reserva.getBalones().getIdBalon() != null)
                                ? new ReservaBalonRequest(reserva.getBalones().getIdBalon().getRutaImagen())
                                : null)
                .build();
    }
}

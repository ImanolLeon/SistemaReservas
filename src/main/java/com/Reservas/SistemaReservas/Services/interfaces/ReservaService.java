package com.Reservas.SistemaReservas.Services.interfaces;

import com.Reservas.SistemaReservas.Entity.Enum.EstadoReserva;
import com.Reservas.SistemaReservas.Entity.Reserva;
import com.Reservas.SistemaReservas.Repository.ReservaRepository;
import com.Reservas.SistemaReservas.Services.implementation.ReservaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService implements ReservaServiceImpl {

    @Autowired
    private ReservaRepository reservaRepository;


    @Override
    public boolean validarReserva(Reserva reserva) {
        return true;
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

    @Override
    public Reserva guardar(Reserva reserva) {

        if ( reserva==null){
            throw new RuntimeException("La reserva enviada es un valor nulo");
        }

        return reservaRepository.save(reserva);

    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) {
        return Optional.empty();
    }




}

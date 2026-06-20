package com.Reservas.SistemaReservas.Repository.Especificaciones;

import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.Entity.Reserva;
import com.Reservas.SistemaReservas.dto.request.ReservaRequestEspecification;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ReservaEspecificaciones implements Specification<Reserva> {

    private final ReservaRequestEspecification reservaRequestEspecification;

    public ReservaEspecificaciones(ReservaRequestEspecification reservaRequestEspecification) {
        this.reservaRequestEspecification = reservaRequestEspecification;
    }

    @Override
    public @Nullable Predicate toPredicate(Root<Reserva> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if(reservaRequestEspecification.dia() != null){
            Predicate dayPredicate = root.get("dia").in(reservaRequestEspecification.dia());
            predicates.add(dayPredicate);
        }

        if(reservaRequestEspecification.fecha()!= null){
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), reservaRequestEspecification.fecha());
            predicates.add(predicate);
        }

        if(reservaRequestEspecification.nombreCampoFutbol() != null){
            //Cuando quieres hacer join
            Join<Reserva, CampoFutbol> campoJoin = root.join("idCampoFutbol");

            //Ahora el root es de la tabla CampoFutbol
            Predicate predicate = criteriaBuilder.like(campoJoin.get("nombre"),"%" + reservaRequestEspecification.nombreCampoFutbol() + "%");
            predicates.add(predicate);
        }

        if(reservaRequestEspecification.horaInicio() != null){
            Predicate predicate = criteriaBuilder.greaterThan(root.get("horaInicio"),reservaRequestEspecification.horaInicio());
            predicates.add(predicate);
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

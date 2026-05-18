package com.Reservas.SistemaReservas.Entity;

import com.Reservas.SistemaReservas.Entity.Enum.EstadoReserva;
import com.Reservas.SistemaReservas.Entity.security.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "id_Usuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;

    @ManyToOne
    @JoinColumn(name = "idCampoFutbol")
    private CampoFutbol idCampoFutbol;

    @Column(name = "Hora_inicio")
    private LocalTime horaInicio;

    @Column(name="Hora_final")
    private LocalTime horaFinal;

    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "dia")
    private String dia;

    @Column(name = "precio")
    private double precio;

    @OneToOne()
    @JoinColumn(name = "reserva_balon_id")
    private ReservaBalon balones;

    @OneToOne()
    @JoinColumn(name = "reserva_ camiseta")
    private ReservaCamiseta camisetas;
}

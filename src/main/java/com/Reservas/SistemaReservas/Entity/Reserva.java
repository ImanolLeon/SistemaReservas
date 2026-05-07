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

    @ManyToOne(targetEntity = Usuario.class )
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;

    @ManyToOne
    @JoinColumn(name = "idCampoFutbol")
    private  CampoFutbol idCampoFutbol;

    @Column(name = "HoraInicio")
    private LocalTime horaInicio;

    @Column(name="HoraFinal")
    private LocalTime horaFinal;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "precio")
    private double precio;

    @OneToOne(mappedBy = "reservaBalon")
    private ReservaBalon balones;

    @OneToOne(mappedBy ="reservaCamiseta")
    private ReservaCamiseta camisetas;
}

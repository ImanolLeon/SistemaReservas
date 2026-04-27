package com.Reservas.SistemaReservas.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ReservaBalon")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReservaBalon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idReserva")
    private Reserva reservaBalon;

    @ManyToOne
    @JoinColumn(name = "idBalon")
    private Balon idBalon;
}

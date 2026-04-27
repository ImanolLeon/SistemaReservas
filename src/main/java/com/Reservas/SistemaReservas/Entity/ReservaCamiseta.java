package com.Reservas.SistemaReservas.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ReservaCamiseta")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ReservaCamiseta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservaId")
    private Reserva reservaCamiseta;

    @ManyToOne(targetEntity = Camiseta.class)
    @JoinColumn(name = "camisetaId")
    private Camiseta camiseta_id;
}

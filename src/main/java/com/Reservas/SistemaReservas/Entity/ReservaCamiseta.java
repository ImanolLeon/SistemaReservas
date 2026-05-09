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

    @OneToOne(mappedBy = "camisetas")
    private Reserva reservaCamiseta;

    @ManyToOne()
    @JoinColumn(name = "camisetaId")
    private Camiseta camiseta_id;
}

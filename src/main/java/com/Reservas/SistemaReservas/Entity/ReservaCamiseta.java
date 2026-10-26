package com.Reservas.SistemaReservas.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToOne(mappedBy = "camisetas")
    private Reserva reservaCamiseta;

    @ManyToOne()
    @JoinColumn(name = "camisetaId")
    private Camiseta camisetaId;
}

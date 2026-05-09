package com.Reservas.SistemaReservas.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

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

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "balones")
    private Reserva reservaBalon;

    @ManyToOne
    @JoinColumn(name = "idBalon")
    private Balon idBalon;
}

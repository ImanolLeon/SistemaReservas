package com.Reservas.SistemaReservas.Entity;

import com.Reservas.SistemaReservas.Entity.Enum.CondicionCampo;
import com.Reservas.SistemaReservas.Entity.Enum.SuperficieCampo;
import com.Reservas.SistemaReservas.Entity.Enum.TamanoCampo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "CampoFutbol")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CampoFutbol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String  nombre;
    @Enumerated(EnumType.STRING)
    private CondicionCampo condicionCampo;

    @Enumerated(EnumType.STRING)
    private TamanoCampo tamanoCampo;

    @OneToMany(cascade = CascadeType.ALL ,mappedBy = "idCampoFutbol")
    private List<Reserva> reserva;

    @Enumerated(EnumType.STRING)
    private SuperficieCampo superficieCampo;


}

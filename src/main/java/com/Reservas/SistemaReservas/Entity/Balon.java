package com.Reservas.SistemaReservas.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Balon")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Balon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBalon;
    @Column(name = "tamano",nullable = false)
    private int tamano;
    @Column(name = "Imagen")
    private String rutaImagen;

    @OneToMany(targetEntity = ReservaBalon.class,cascade = CascadeType.ALL,mappedBy = "idBalon")
    private List<ReservaBalon> balones;

}

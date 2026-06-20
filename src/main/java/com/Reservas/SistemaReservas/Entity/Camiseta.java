package com.Reservas.SistemaReservas.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;

import java.util.List;

@Entity
@Table(name = "Camiseta")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Camiseta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "color")
    private String color;
    @Column(name = "rutaImagen")
    private String rutaImagen;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "camisetaId")
    private List<ReservaCamiseta>  reservaCamiseta;
}

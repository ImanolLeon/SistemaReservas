package com.Reservas.SistemaReservas.Entity;

import com.Reservas.SistemaReservas.Entity.Enum.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(name = "dni",length = 8,unique = true)
    private String dni ;

    @Column(name = "nombre",nullable = false)
    private String nombre;

    @Column(name = "apellido",nullable = false)
    private String apellido;

    @Column(name = "numero",length = 9)
    private String numero;

    @Column(name = "Email", unique = true)
    private String email;

    private String contrasena;

    @OneToMany(targetEntity = Reserva.class, mappedBy = "usuario",cascade = CascadeType.ALL )
    private List<Reserva> reserva ;
}

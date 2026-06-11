package com.Reservas.SistemaReservas.Entity.security;

import com.Reservas.SistemaReservas.Entity.Enum.Rol;
import com.Reservas.SistemaReservas.Entity.Reserva;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "dni",length = 8,unique = true)
    private String dni ;

    @Column(name = "nombre",nullable = false)
    private String nombre;

    @Column(nullable = false,unique = true)
    private  String username;

    @Column(name = "apellido",nullable = false)
    private String apellido;

    @Column(name = "numero",length = 9)
    private String numero;

    @Column(name = "Email", unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Reserva> reserva ;

    //variables spring security
    private boolean isEnable;
    private boolean isCredentialsNonExpired;
    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
            @JoinTable(
                    name = "user_rol",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "rol_id")
            )
    Set<RolEntity> roles = new HashSet<>();
}

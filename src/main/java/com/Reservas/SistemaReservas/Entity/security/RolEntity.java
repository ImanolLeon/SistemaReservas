package com.Reservas.SistemaReservas.Entity.security;

import com.Reservas.SistemaReservas.Entity.Enum.Rol;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Rol_entity")
public class RolEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Rol roles;
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    Set<Usuario> usuarios = new HashSet<>();

    @ManyToMany(fetch =FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
            @JoinTable(
                    name = "rol_permisos",
                    joinColumns = @JoinColumn(name = "rol"),
                    inverseJoinColumns = @JoinColumn(name = "permisos")
            )
    Set<PermissionEntity> permisos = new HashSet<>();
}

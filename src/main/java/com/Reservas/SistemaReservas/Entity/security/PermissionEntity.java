package com.Reservas.SistemaReservas.Entity.security;

import com.Reservas.SistemaReservas.Entity.Enum.Permissions;
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
@Table(name = "permisos_entity")
public class PermissionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Permissions permisos;

    @JsonIgnore
    @ManyToMany(mappedBy = "permisos")
    Set<RolEntity> roles = new HashSet<>();


}

package com.Reservas.SistemaReservas.Entity.security;

import jakarta.persistence.*;
import lombok.*;

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


}

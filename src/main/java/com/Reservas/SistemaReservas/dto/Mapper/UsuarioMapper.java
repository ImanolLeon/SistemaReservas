package com.Reservas.SistemaReservas.dto.Mapper;

import com.Reservas.SistemaReservas.Entity.Enum.Permissions;
import com.Reservas.SistemaReservas.Entity.Enum.Rol;
import com.Reservas.SistemaReservas.Entity.security.PermissionEntity;
import com.Reservas.SistemaReservas.Entity.security.RolEntity;
import com.Reservas.SistemaReservas.Entity.security.Usuario;
import com.Reservas.SistemaReservas.dto.request.UsuarioRequest;
import com.Reservas.SistemaReservas.dto.response.UsuarioResponse;

import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario usuarioToUsuario(UsuarioRequest usuarioRequest){
        RolEntity rolUser = RolEntity.builder()
                .roles(Rol.USUARIO)
                .permisos(Set.of(
                        PermissionEntity.builder()
                                .permisos(Permissions.VER)
                                .build()
                ))
                .build();
        return  Usuario.builder()
                .dni(usuarioRequest.dni())
                .email(usuarioRequest.email())
                .nombre(usuarioRequest.nombre())
                .password(usuarioRequest.password())
                .roles(Set.of(rolUser))
                .username(usuarioRequest.username())
                .apellido(usuarioRequest.apellido())
                .numero(usuarioRequest.numero())
                .reserva(usuarioRequest.reservas())
                .isEnable(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .build();
    }

    public static UsuarioResponse usuarioReponseToUsuario (Usuario usuario){

        return  UsuarioResponse.builder()
                .nombre(usuario.getNombre())
                .username(usuario.getUsername())
                .apellido(usuario.getApellido())
                .numero(usuario.getNumero())
                .email(usuario.getEmail())
                .dni(usuario.getDni())
                .rol(usuario.getRoles().stream().map(e -> e.getRoles().name()).collect(Collectors.joining(",")))
                .build();
    }
}

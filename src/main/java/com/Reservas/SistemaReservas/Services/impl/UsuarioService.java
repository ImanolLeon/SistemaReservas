package com.Reservas.SistemaReservas.Services.impl;

import com.Reservas.SistemaReservas.Entity.Enum.Permissions;
import com.Reservas.SistemaReservas.Entity.Enum.Rol;
import com.Reservas.SistemaReservas.Entity.security.PermissionEntity;
import com.Reservas.SistemaReservas.Entity.security.RolEntity;
import com.Reservas.SistemaReservas.Entity.security.Usuario;
import com.Reservas.SistemaReservas.Repository.UsuarioRepository;
import com.Reservas.SistemaReservas.Services.reglas.UsuarioServiceImpl;
import com.Reservas.SistemaReservas.dto.Mapper.UsuarioMapper;
import com.Reservas.SistemaReservas.dto.request.UsuarioRequest;
import com.Reservas.SistemaReservas.dto.response.UsuarioResponse;
import com.Reservas.SistemaReservas.excepcion.ApiExcepcion;
import com.Reservas.SistemaReservas.excepcion.MensajesExcepction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioService implements UsuarioServiceImpl {

    private UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioResponse> findAll() {

        return usuarioRepository.findAll().stream()
                .map(entidad -> new UsuarioResponse(
                        entidad.getDni(),
                        entidad.getNombre(),
                        entidad.getUsername(),
                        entidad.getApellido(),
                        entidad.getNumero(),
                        entidad.getEmail(),
                        entidad.getRoles().stream().map(e -> e.getRoles().name()).collect(Collectors.joining(",")
                        ))).toList();
    }

    @Override
    public UsuarioResponse save(UsuarioRequest usuarioRequest) {

    Usuario usuario= usuarioRepository.save(UsuarioMapper.usuarioToUsuario(usuarioRequest));
        return UsuarioMapper.usuarioReponseToUsuario(usuario);
    }

    @Override
    public boolean hacerAdmin(Long id) {
        Usuario  usuario = usuarioRepository.findById(id).orElseThrow(
                ()->  ApiExcepcion.userNotFound(MensajesExcepction.USUARIO_NO_ENCONTRADO.formatted(id))
        );


        RolEntity admin = RolEntity.builder()
                .permisos(Set.of(
                        PermissionEntity.builder()
                                .permisos(Permissions.CREAR)
                                .build(),
                        PermissionEntity.builder()
                                .permisos(Permissions.VER)
                                .build(),
                        PermissionEntity.builder()
                                .permisos(Permissions.ACTUALIZAR)
                                .build(),
                        PermissionEntity.builder()
                                .permisos(Permissions.ELIMINAR)
                                .build(),
                        PermissionEntity.builder()
                                .permisos(Permissions.AUTORIZAR)
                                .build()
                ))
                .roles(Rol.ADMIN)
                .build();

        usuario.getRoles().add(admin);

        //Guardar
        usuarioRepository.save(usuario);
        return true;
    }




}

package com.Reservas.SistemaReservas.Services.impl;

import com.Reservas.SistemaReservas.Entity.security.Usuario;
import com.Reservas.SistemaReservas.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException("Nombre de usuario no encontrado"));

        List<SimpleGrantedAuthority> grantedAuthorities= new ArrayList<>();
        usuario.getRoles().stream()
                .forEach(roles -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_".concat(roles.getRoles().name()))));

        usuario.getRoles().stream().
                flatMap(roles -> roles.getPermisos().stream())
                .forEach(permisos -> grantedAuthorities.add(new SimpleGrantedAuthority(permisos.getPermisos().name())));

        return  new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.isEnable(),
                usuario.isAccountNonExpired(),
                usuario.isCredentialsNonExpired(),
                usuario.isAccountNonLocked(),
                grantedAuthorities
        );
    }
}

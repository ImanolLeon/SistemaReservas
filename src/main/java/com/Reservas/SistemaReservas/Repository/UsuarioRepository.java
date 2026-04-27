package com.Reservas.SistemaReservas.Repository;

import com.Reservas.SistemaReservas.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Long, Usuario> {
    Usuario findByRol(String rol);
}

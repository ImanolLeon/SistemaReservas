package com.Reservas.SistemaReservas;

import com.Reservas.SistemaReservas.Entity.Enum.Permissions;
import com.Reservas.SistemaReservas.Entity.Enum.Rol;
import com.Reservas.SistemaReservas.Entity.security.PermissionEntity;
import com.Reservas.SistemaReservas.Entity.security.RolEntity;
import com.Reservas.SistemaReservas.Entity.security.Usuario;
import com.Reservas.SistemaReservas.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository){
		return args -> {

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
			RolEntity user = RolEntity.builder()
					.permisos(Set.of(
							PermissionEntity.builder()
									.permisos(Permissions.VER)
									.build()
					))
					.roles(Rol.USUARIO)
					.build();
			RolEntity recepcion = RolEntity.builder()
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
					.roles(Rol.RECEPCION)
					.build();

			Usuario usuario1 = Usuario.builder()
					.dni("61230111")
					.email("imanol@gmail.com")
					.apellido("Leon")
					.nombre("Imanol")
					.username("admin")
					.password(new BCryptPasswordEncoder().encode("admin"))
					.isEnable(true)
					.isAccountNonExpired(true)
					.isAccountNonLocked(true)
					.isCredentialsNonExpired(true)
					.roles(Set.of(admin))
					.build();

			Usuario usuario2= Usuario.builder()
					.dni("61230112")
					.email("juan@gmail.com")
					.apellido("Prado")
					.nombre("recepcion")
					.username("recepcion")
					.password(new BCryptPasswordEncoder().encode("recepcion"))
					.isEnable(true)
					.isAccountNonExpired(true)
					.isAccountNonLocked(true)
					.isCredentialsNonExpired(true)
					.roles(Set.of(recepcion))
					.build();

			Usuario usuario3= Usuario.builder()
					.dni("61230112")
					.email("juan@gmail.com")
					.apellido("Prado")
					.nombre("recepcion")
					.username("recepcion")
					.password(new BCryptPasswordEncoder().encode("recepcion"))
					.isEnable(true)
					.isAccountNonExpired(true)
					.isAccountNonLocked(true)
					.isCredentialsNonExpired(true)
					.roles(Set.of(recepcion))
					.build();

		};
	}
}

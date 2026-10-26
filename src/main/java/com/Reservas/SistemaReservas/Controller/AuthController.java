package com.Reservas.SistemaReservas.Controller;

import com.Reservas.SistemaReservas.Entity.security.Usuario;
import com.Reservas.SistemaReservas.Repository.UsuarioRepository;
import com.Reservas.SistemaReservas.Services.reglas.UsuarioServiceImpl;
import com.Reservas.SistemaReservas.dto.request.LoginRequest;
import com.Reservas.SistemaReservas.dto.request.UsuarioRequest;
import com.Reservas.SistemaReservas.dto.response.LoginResponse;
import com.Reservas.SistemaReservas.dto.response.UsuarioActualResponse;
import com.Reservas.SistemaReservas.dto.response.UsuarioResponse;
import com.Reservas.SistemaReservas.excepcion.ApiExcepcion;
import com.Reservas.SistemaReservas.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioServiceImpl usuarioService;
    private final JwtService jwtService;

    /**
     * POST /auth/login -> devuelve el token JWT
     * Body: { "username": "...", "password": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        } catch (AuthenticationException e) {
            throw new ApiExcepcion("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }

        Usuario usuario = usuarioRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ApiExcepcion("Usuario no encontrado", HttpStatus.NOT_FOUND));

        String token = jwtService.generarToken(usuario);

        return ResponseEntity.ok(new LoginResponse(
                token,
                "Bearer",
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                extraerRoles(usuario)
        ));
    }

    /**
     * GET /auth/me -> devuelve el usuario del token actual
     */
    @GetMapping("/me")
    public ResponseEntity<UsuarioActualResponse> me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ApiExcepcion("Usuario no encontrado", HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new UsuarioActualResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getDni(),
                usuario.getNumero(),
                usuario.getEmail(),
                extraerRoles(usuario)
        ));
    }

    /**
     * POST /auth/register -> crea un usuario con rol USUARIO
     */
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody UsuarioRequest request) {
        if (request.username() == null || request.username().isBlank()) {
            throw new ApiExcepcion("El username es obligatorio", HttpStatus.BAD_REQUEST);
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new ApiExcepcion("La password es obligatoria", HttpStatus.BAD_REQUEST);
        }
        if (usuarioRepository.findByUsername(request.username()).isPresent()) {
            throw new ApiExcepcion("Ese username ya esta en uso", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(request));
    }

    private List<String> extraerRoles(Usuario usuario) {
        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            return List.of("USUARIO");
        }
        return usuario.getRoles().stream()
                .map(rol -> rol.getRoles().name())
                .toList();
    }
}

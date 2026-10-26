package com.Reservas.SistemaReservas.security;

import com.Reservas.SistemaReservas.Entity.security.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * Genera el token JWT para un usuario ya autenticado.
     */
    public String generarToken(Usuario usuario) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + expirationMs);

        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("id", usuario.getId())
                .claim("nombre", usuario.getNombre())
                .claim("apellido", usuario.getApellido())
                .claim("roles", extraerRoles(usuario))
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Devuelve el username (subject) del token, o null si el token no es valido.
     */
    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * Devuelve los roles del token (ej: ["ADMIN","USUARIO"]).
     */
    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token) {
        List<String> roles = parseClaims(token).get("roles", List.class);
        return roles == null ? List.of() : roles;
    }

    public boolean esValido(String token, UserDetails userDetails) {
        try {
            String username = getUsername(token);
            return username != null
                    && username.equals(userDetails.getUsername())
                    && !esExpirado(token);
        } catch (Exception e) {
            return false;
        }
    }

    public long getExpirationMs() {
        return expirationMs;
    }

    private boolean esExpirado(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Set<String> extraerRoles(Usuario usuario) {
        Set<String> roles = usuario.getRoles().stream()
                .map(rol -> rol.getRoles().name())
                .collect(Collectors.toSet());
        // Si el usuario todavia no tiene rol en BD, se trata como USUARIO comun
        if (roles.isEmpty()) {
            roles.add("USUARIO");
        }
        return roles;
    }
}

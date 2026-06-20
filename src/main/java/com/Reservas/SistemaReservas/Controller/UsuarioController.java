package com.Reservas.SistemaReservas.Controller;

import com.Reservas.SistemaReservas.Entity.security.Usuario;
import com.Reservas.SistemaReservas.Repository.UsuarioRepository;
import com.Reservas.SistemaReservas.Services.impl.UsuarioService;
import com.Reservas.SistemaReservas.dto.request.UsuarioRequest;
import com.Reservas.SistemaReservas.dto.response.UsuarioResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@PreAuthorize("permitAll()")
public class UsuarioController {

    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;

    @PostMapping("/save")
    public ResponseEntity<UsuarioResponse> save(@RequestBody UsuarioRequest usuarioRequest){
      return ResponseEntity.status(HttpStatus.CREATED)
              .body(usuarioService.save(usuarioRequest));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UsuarioResponse>> findAll(){
        return  ResponseEntity.status(HttpStatus.OK).body(
                usuarioService.findAll()
        );
    }

    @GetMapping("/findAllEntity")
    public ResponseEntity<List<Usuario>> findAllUsuario(){
        return  ResponseEntity.status(HttpStatus.OK).body(
                usuarioRepository.findAll()
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/conv/{id}")
    public ResponseEntity<Void> convertirAdmin(@PathVariable Long id){
        boolean admin = usuarioService.hacerAdmin(id);
        if(admin){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

}

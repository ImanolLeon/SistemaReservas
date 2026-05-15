package com.Reservas.SistemaReservas.Controller;

import com.Reservas.SistemaReservas.Entity.CampoFutbol;
import com.Reservas.SistemaReservas.Services.interfaces.CampoFutbolService;
import com.Reservas.SistemaReservas.dto.request.CampoFutbolRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campoFutbol")
public class CampoFutboLController {
    @Autowired
    private CampoFutbolService campoFutbolService;

    @GetMapping("/findAll")
    public ResponseEntity<List<CampoFutbolRequest>> listAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(campoFutbolService.listAll());
    }
    @GetMapping("/findAllEntity")
    public ResponseEntity<List<CampoFutbol>> listAllEntity(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(campoFutbolService.listarTodo());
    }

    @PostMapping("/save")
    public ResponseEntity<CampoFutbolRequest> save(@Valid @RequestBody CampoFutbolRequest campoFutbolRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(campoFutbolService.save(campoFutbolRequest));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CampoFutbol> findById(@Valid @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(campoFutbolService.findById(id));
    }

    @GetMapping("/findByTamano/{tamanoCampo}")
    public ResponseEntity<List<CampoFutbolRequest>> findByTamanoCampo(@PathVariable String tamanoCampo){
        return ResponseEntity.ok(campoFutbolService.findByTamanoCampo(tamanoCampo));
    }
    @GetMapping("/findBySuperficie/{superficie}")
    public ResponseEntity<List<CampoFutbolRequest>> findBySuperficieCampo(@PathVariable String superficie){
        return ResponseEntity.ok(campoFutbolService.findBySuperficieCampo(superficie));
    }
    @GetMapping("/findByEstado/{estado}")
    public ResponseEntity<List<CampoFutbolRequest>> findByEstadoCampo(@PathVariable String estado){
        return ResponseEntity.ok(campoFutbolService.findByCondicionCampo(estado));
    }





}

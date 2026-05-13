package com.Reservas.SistemaReservas.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prueba")
public class PruebaSecurity {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/saludo")
    public String saludo(){
        return "hola admin";
    }
}

package com.Reservas.SistemaReservas.Controller;

import com.Reservas.SistemaReservas.Entity.Camiseta;
import com.Reservas.SistemaReservas.Services.impl.CamisetaService;
import com.Reservas.SistemaReservas.dto.request.CamisetaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/camiseta")
public class CamisetaController {

    @Autowired
    private CamisetaService camisetaService;

    @PostMapping("/save")
    public CamisetaDto save (@RequestBody CamisetaDto camisetaDto){
        return camisetaService.save(camisetaDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/fingById/{id}")
    public Camiseta findById(@PathVariable Long id){
         return camisetaService.findById(id);
    }

    @GetMapping("/listAll")
    public List<CamisetaDto> listAll(){
        return  camisetaService.findAll();
    }



}

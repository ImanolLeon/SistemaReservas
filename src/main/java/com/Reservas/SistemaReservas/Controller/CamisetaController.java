package com.Reservas.SistemaReservas.Controller;

import com.Reservas.SistemaReservas.Entity.Camiseta;
import com.Reservas.SistemaReservas.Services.interfaces.CamisetaService;
import com.Reservas.SistemaReservas.dto.request.CamisetaDto;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/fingById/{id}")
    public Camiseta findById(@PathVariable Long id){
         return camisetaService.findById(id);
    }
    @GetMapping("/listAll")
    public List<CamisetaDto> listAll(){
        return  camisetaService.findAll();
    }



}

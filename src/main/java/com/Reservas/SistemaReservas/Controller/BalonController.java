package com.Reservas.SistemaReservas.Controller;

import com.Reservas.SistemaReservas.Entity.Balon;
import com.Reservas.SistemaReservas.Services.interfaces.BalonService;
import com.Reservas.SistemaReservas.dto.request.BalonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/balon")
@PreAuthorize("hasRole('ADMIN')")
public class BalonController {

    @Autowired
    private BalonService balonService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<BalonDto>> listarBalon(){
        List<BalonDto> balones = balonService.listAll();
       return ResponseEntity.ok(balones);
    }

    @PostMapping("/guardar")
    public ResponseEntity<BalonDto> guardarBalon(@RequestBody BalonDto balonDto){
        return ResponseEntity.ok(balonService.save(balonDto));
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Balon> buscarPoId(@PathVariable Long id){
       return ResponseEntity.ok(balonService.findById(id));
    }

    @GetMapping("buscarPorTamano/{tamano}")
    public ResponseEntity<List<BalonDto>> buscarPorTamanoValon(@PathVariable String tamano) {
        return ResponseEntity.ok(balonService.findByTamanoBalon(tamano));
    }
}

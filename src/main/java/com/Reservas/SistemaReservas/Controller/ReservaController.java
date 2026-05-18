package com.Reservas.SistemaReservas.Controller;

import com.Reservas.SistemaReservas.Services.interfaces.ReservaService;
import com.Reservas.SistemaReservas.dto.request.ReservaRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reserva")
@AllArgsConstructor
public class ReservaController {
 private ReservaService reservaService;


 @PostMapping("save")
    public ResponseEntity<ReservaRequest> save(@RequestBody ReservaRequest request){
     return  ResponseEntity.status(HttpStatus.OK).body(
             reservaService.guardar(request)
     );
 }



}

package com.Reservas.SistemaReservas.Controller;

import com.Reservas.SistemaReservas.Services.impl.ReservaBalonService;
import com.Reservas.SistemaReservas.dto.response.ReservaBalonResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ReservaBalon")
@AllArgsConstructor
public class ReservaBalonController {

    private ReservaBalonService reservaBalonService;

    @PostMapping("/save/{idReserva}/{idBalon}")
    public ResponseEntity<ReservaBalonResponse> save(
            @PathVariable Long idReserva, @PathVariable Long idBalon){

        return ResponseEntity.status(HttpStatus.CREATED).body(
                reservaBalonService.save(idReserva, idBalon)
        );
    }






}

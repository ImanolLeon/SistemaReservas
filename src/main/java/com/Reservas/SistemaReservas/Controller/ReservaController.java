package com.Reservas.SistemaReservas.Controller;

import com.Reservas.SistemaReservas.Entity.Reserva;
import com.Reservas.SistemaReservas.Services.impl.ReservaService;
import com.Reservas.SistemaReservas.dto.request.ReservaBalonRequest;
import com.Reservas.SistemaReservas.dto.request.ReservaRequest;
import com.Reservas.SistemaReservas.dto.request.ReservaRequestEspecification;
import com.Reservas.SistemaReservas.dto.response.ReservaBalonResponse;
import com.Reservas.SistemaReservas.dto.response.ReservaReponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reserva")
@AllArgsConstructor
public class ReservaController {
   private ReservaService reservaService;

   @PostMapping("/cambiarEstado/{id}")
   public ResponseEntity<Boolean> cancelarReserva(@PathVariable Long id){
    return  ResponseEntity.status(HttpStatus.OK)
            .body(reservaService.cancelarReserva(id));
   }

   @PostMapping("/reservarBalon/{idReserva}/{idBalon}")
   public ResponseEntity<ReservaBalonResponse> reservarBalon(@PathVariable Long idReserva, @PathVariable Long idBalon){
     return ResponseEntity.status(HttpStatus.CREATED)
             .body(reservaService.guardarReservaBalon(idReserva,idBalon));
   }

   @DeleteMapping("/borrarReservaBalon/{idReserva}")
   public ResponseEntity<Void> borrarReservaBalon(@PathVariable Long idReserva){
       reservaService.eliminarReservaBalon(idReserva);
       return ResponseEntity.status(HttpStatus.OK).build();
   }
   @GetMapping("/findAll")
   public ResponseEntity<List<ReservaReponse>> findAll(){
    return ResponseEntity.status(HttpStatus.OK)
            .body(reservaService.findAll());
   }


   @GetMapping("/findByDia/{dia}")
   public ResponseEntity<List<ReservaReponse>> findByDia(@PathVariable String dia){
    return ResponseEntity.ok(reservaService.findByDia(dia));
   }

   @GetMapping("/findByEstadoReserva/{estado}")
   public ResponseEntity<List<ReservaReponse>> findByEstadoReserva(@PathVariable String estado){
    return ResponseEntity.status(HttpStatus.OK).
            body(reservaService.findByEstadoReserva(estado));
   }

   @PostMapping("/save")
   public ResponseEntity<ReservaRequest> save(@RequestBody ReservaRequest request){
    return ResponseEntity.status(HttpStatus.CREATED).body(
            reservaService.guardar(request));
   }

   @GetMapping("/findById/{id}")
   public ResponseEntity<Reserva> findById(@PathVariable Long id){
    return ResponseEntity.status(HttpStatus.OK).body(
            reservaService.buscarPorId(id));
   }

   //Buscar por nombre
   @GetMapping("/findByCampo/{id}")
    public ResponseEntity<List<ReservaReponse>> findByCampo(@PathVariable Long id){
       return ResponseEntity.status(HttpStatus.OK).body(
               reservaService.findByCampoFutbol(id)
       );
   }

   @GetMapping("/filtrar")
    ResponseEntity<List<ReservaReponse>>
   filtrarReservas(@RequestParam(required = false) LocalDate fecha,
                   @RequestParam(required = false) String nombreCampoFutbol,
                   @RequestParam(required = false) String dia,
                   @RequestParam(required = false) LocalTime horaInicio){
       ReservaRequestEspecification reservaRequestEspecification =
               new ReservaRequestEspecification(fecha,dia,nombreCampoFutbol,horaInicio);
    return  ResponseEntity.status(HttpStatus.OK).body(reservaService.listarPorParametros(reservaRequestEspecification));
   }

   //Buscar por nombre









}

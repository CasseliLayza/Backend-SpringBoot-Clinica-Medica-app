package com.backend.clinica.controller;


import com.backend.clinica.dto.request.TurnoRequest;
import com.backend.clinica.dto.response.TurnoResponse;
import com.backend.clinica.service.TurnoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/turnos")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping
    public ResponseEntity<List<TurnoResponse>> obtenerTodosLosTurnos() {
        return new ResponseEntity<>(turnoService.listarTurnos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TurnoResponse> crearTurno(@RequestBody @Valid TurnoRequest Turno) {
        return new ResponseEntity<>(turnoService.guardarTurno(Turno), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponse> obtenerTurnoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(turnoService.buscarTurno(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoResponse> actualizarTurno(@PathVariable Long id, @RequestBody @Valid TurnoRequest Turno) {
        return new ResponseEntity<>(turnoService.actualizarTurno(Turno, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Long id) {
        turnoService.eliminarTurno(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

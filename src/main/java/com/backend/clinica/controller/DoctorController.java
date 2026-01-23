package com.backend.clinica.controller;

import com.backend.clinica.dto.request.DoctorRequest;
import com.backend.clinica.dto.response.DoctorResponse;
import com.backend.clinica.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctores")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> obtenerTodosLosDoctors() {
        return new ResponseEntity<>(doctorService.listarDoctores(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> crearDoctor(@RequestBody @Valid DoctorRequest Doctor) {
        return new ResponseEntity<>(doctorService.guardarDoctor(Doctor), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> obtenerDoctorPorId(@PathVariable Long id) {
        return new ResponseEntity<>(doctorService.buscarDoctor(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> actualizarDoctor(@PathVariable Long id, @RequestBody @Valid DoctorRequest Doctor) {
        return new ResponseEntity<>(doctorService.actualizarDoctor(Doctor, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDoctor(@PathVariable Long id) {
        doctorService.eliminarDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}

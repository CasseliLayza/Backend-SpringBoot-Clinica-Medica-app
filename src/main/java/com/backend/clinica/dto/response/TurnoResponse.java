package com.backend.clinica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurnoResponse {
    private Long id;
    private DoctorResponse doctor;
    private PacienteResponse paciente;
    private LocalDateTime fechaHora;
}

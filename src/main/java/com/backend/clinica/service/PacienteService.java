package com.backend.clinica.service;

import com.backend.clinica.dto.request.PacienteRequest;
import com.backend.clinica.dto.response.PacienteResponse;

import java.util.List;

public interface PacienteService {

    List<PacienteResponse> listarPacientes();

    PacienteResponse buscarPaciente(Long id);

    PacienteResponse guardarPaciente(PacienteRequest paciente);

    PacienteResponse actualizarPaciente(PacienteRequest paciente, Long id);

    void eliminarPaciente(Long id);


}

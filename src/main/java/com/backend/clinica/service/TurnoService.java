package com.backend.clinica.service;

import com.backend.clinica.dto.request.TurnoRequest;
import com.backend.clinica.dto.response.TurnoResponse;

import java.util.List;

public interface TurnoService {

    List<TurnoResponse> listarTurnos();

    TurnoResponse buscarTurno(Long id);

    TurnoResponse guardarTurno(TurnoRequest turno);

    TurnoResponse actualizarTurno(TurnoRequest turno, Long id);

    void eliminarTurno(Long id);
}

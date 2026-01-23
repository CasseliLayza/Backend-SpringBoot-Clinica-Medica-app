package com.backend.clinica.service;

import com.backend.clinica.dto.request.DoctorRequest;
import com.backend.clinica.dto.response.DoctorResponse;

import java.util.List;

public interface DoctorService {

    List<DoctorResponse> listarDoctores();

    DoctorResponse buscarDoctor(Long id);

    DoctorResponse guardarDoctor(DoctorRequest doctor);

    DoctorResponse actualizarDoctor(DoctorRequest doctor, Long id);

    void eliminarDoctor(Long id);
}

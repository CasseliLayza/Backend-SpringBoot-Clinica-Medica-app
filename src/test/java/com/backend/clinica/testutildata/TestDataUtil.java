package com.backend.clinica.testutildata;

import com.backend.clinica.dto.request.DoctorRequest;
import com.backend.clinica.dto.request.DomicilioRequest;
import com.backend.clinica.dto.request.PacienteRequest;
import com.backend.clinica.dto.request.TurnoRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestDataUtil {

    public static PacienteRequest crearPacienteRequest() {
        return new PacienteRequest(
                "Casse", "Last", "123455678",
                crearDomicilioRequest(),
                "casse@gmail.com", "casseLopez", "gshshs",
                LocalDate.of(2026, 1, 28), true, true
        );
    }

    public static DoctorRequest createDoctorRequest() {
        return new DoctorRequest("Casse", "Last", "MAT1234",
                "casse@gmail.com", "casseLopez",
                "gshshs", true, true);
    }

    public static DomicilioRequest crearDomicilioRequest() {
        return new DomicilioRequest(
                "Calle Falsa", "123", "Springfield", "Illinois"
        );
    }

    public static TurnoRequest crearTurnoRequest(
            PacienteRequest pacienteRequest,
            DoctorRequest doctorRequest,
            LocalDateTime fechaHora) {
        return new TurnoRequest(
                doctorRequest,
                pacienteRequest,
                fechaHora
        );
    }


}

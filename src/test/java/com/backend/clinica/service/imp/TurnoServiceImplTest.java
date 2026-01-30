package com.backend.clinica.service.imp;

import com.backend.clinica.dto.request.DoctorRequest;
import com.backend.clinica.dto.request.PacienteRequest;
import com.backend.clinica.dto.request.TurnoRequest;
import com.backend.clinica.dto.response.DoctorResponse;
import com.backend.clinica.dto.response.PacienteResponse;
import com.backend.clinica.dto.response.TurnoResponse;
import com.backend.clinica.exception.ResourceNotFoundException;
import com.backend.clinica.testutildata.TestDataUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoServiceImplTest {

    @Autowired
    private TurnoServiceImpl turnoServiceImpl;

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;

    @Autowired
    private PacienteServiceImpl pacienteServiceImpl;


    @Test
    @Order(1)
    void deberiaCrearTurno_yRetornarTurnoCreado() {

        PacienteRequest pacienteRequest = TestDataUtil.crearPacienteRequest();
        DoctorRequest doctorRequest = TestDataUtil.createDoctorRequest();
        PacienteResponse pacienteResponse = assertDoesNotThrow(() -> {
            return pacienteServiceImpl.guardarPaciente(pacienteRequest);
        });

        DoctorResponse doctorResponse = assertDoesNotThrow(() -> {
            return doctorServiceImpl.guardarDoctor(doctorRequest);
        });

        TurnoRequest turnoRequest = TestDataUtil.crearTurnoRequest(
                pacienteRequest, doctorRequest, LocalDateTime.of(
                        2026, 2, 1,
                        10, 0, 0));

        TurnoResponse turnoResponse = assertDoesNotThrow(() -> {
            return turnoServiceImpl.guardarTurno(turnoRequest);
        });

        assertThat(turnoResponse)
                .isNotNull()
                .extracting(TurnoResponse::getId,
                        turnoResponse1 -> turnoResponse1.getPaciente().getDni(),
                        turnoResponse2 -> turnoResponse2.getDoctor().getMatricula())
                .containsExactly(
                        1L,
                        "123455678",
                        "MAT1234"
                );

    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDeTurnos() {
        List<TurnoResponse> turnos = turnoServiceImpl.listarTurnos();
        assertFalse(turnos.isEmpty());
    }

    @Test
    @Order(3)
    void deberiaBuscarTurnoPorId_yRetornarlo() {
        TurnoResponse turnoResponse = turnoServiceImpl.
                buscarTurno(1L);

        assertThat(turnoResponse)
                .isNotNull()
                .extracting(TurnoResponse::getId,
                        turnoResponse1 -> turnoResponse1.getPaciente().getDni(),
                        turnoResponse2 -> turnoResponse2.getDoctor().getMatricula())
                .containsExactly(
                        1L,
                        "123455678",
                        "MAT1234"
                );
    }

    @Test
    @Order(4)
    void deberiaActualizarLaFechaDeUnTurnoConId1() {
        PacienteRequest pacienteRequest = TestDataUtil.crearPacienteRequest();
        DoctorRequest doctorRequest = TestDataUtil.createDoctorRequest();
        TurnoRequest turnoRequest = TestDataUtil.crearTurnoRequest(
                pacienteRequest, doctorRequest, LocalDateTime.of(
                        2026, 2, 2,
                        11, 0, 0));
        TurnoResponse turnoResponseActualizado = assertDoesNotThrow(() -> {
            return turnoServiceImpl.actualizarTurno(turnoRequest, 1L);
        });
        assertThat(turnoResponseActualizado)
                .isNotNull()
                .extracting(TurnoResponse::getId,
                        turnoResponse1 -> turnoResponse1.getFechaHora())
                .containsExactly(
                        1L,
                        LocalDateTime.of(2026, 2, 2, 11, 0, 0)
                );
    }

    @Test
    @Order(5)
    void deberiaEliminarElTurnoConId1() {
        assertDoesNotThrow(() -> {
            turnoServiceImpl.eliminarTurno(1L);
        });
    }


    @Test
    @Order(6)
    void deberiaLanzarUnaResourceNotFoundExceptionAlBuscarUnTurnoInexistente() {
        Long idTurnoInexistente = 999L;
        assertThrows(ResourceNotFoundException.class, () -> {
            turnoServiceImpl.buscarTurno(idTurnoInexistente);
        });

    }

    @Test
    @Order(7)
    void deberiaDevolverUnaListaVaciaDeTurnosDespuesDeEliminarElTurnoConId1() {
        List<TurnoResponse> turnos = turnoServiceImpl.listarTurnos();
        assertThat(turnos.isEmpty());
    }


}






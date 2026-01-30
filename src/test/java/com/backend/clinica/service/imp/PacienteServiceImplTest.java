package com.backend.clinica.service.imp;

import com.backend.clinica.dto.request.DomicilioRequest;
import com.backend.clinica.dto.request.PacienteRequest;
import com.backend.clinica.dto.response.PacienteResponse;
import com.backend.clinica.exception.DuplicateDNIException;
import com.backend.clinica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacienteServiceImplTest {

    @Autowired
    private PacienteServiceImpl pacienteServiceImpl;

    @Test
    @Order(1)
    void deberiaCrearPaciente_yRetornarPacienteCreado() {
        DomicilioRequest domicilioRequest = new DomicilioRequest(
                "Calle Falsa", "123", "Springfield", "Illinois"
        );
        PacienteRequest pacienteRequest = new PacienteRequest(
                "Casse", "Last", "123455678", domicilioRequest,
                "casse@gmail.com", "casseLopez", "gshshs",
                LocalDate.of(2026, 1, 28), true, true
        );

        PacienteResponse pacienteResponse = assertDoesNotThrow(() -> {
            return pacienteServiceImpl.guardarPaciente(pacienteRequest);
        });
        assertNotNull(pacienteResponse);
        assertNotNull(pacienteResponse.getId());
        assertEquals("Casse", pacienteResponse.getNombre());
        assertEquals("Last", pacienteResponse.getApellido());
        assertEquals("123455678", pacienteResponse.getDni());
    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDePacientes() {
        List<PacienteResponse> pacientees = pacienteServiceImpl.listarPacientes();
        assertNotNull(pacientees);
        assertFalse(pacientees.isEmpty());
    }


    @Test
    @Order(3)
    void deberiaBuscarPacientePorId_yRetornarlo() {
        PacienteResponse pacienteResponse = pacienteServiceImpl.
                buscarPaciente(1L);

        assertThat(pacienteResponse)
                .isNotNull()
                .extracting(PacienteResponse::getId,
                        PacienteResponse::getNombre,
                        PacienteResponse::getApellido,
                        PacienteResponse::getDni)
                .containsExactly(1L, "Casse", "Last", "123455678");

    }

    @Test
    @Order(4)
    void deberiaActualizarElDniDelPacienteConId() {
        DomicilioRequest domicilioRequest = new DomicilioRequest(
                "Calle Falsa", "123", "Springfield", "Illinois"
        );
        PacienteRequest pacienteRequest = new PacienteRequest(
                "Casse", "Last", "123455777", domicilioRequest,
                "casse@gmail.com", "casseLopez", "gshshs",
                LocalDate.of(2026, 1, 28), true, true
        );

        PacienteResponse pacienteResponseActualizado = assertDoesNotThrow(() -> {
            return pacienteServiceImpl.actualizarPaciente(pacienteRequest, 1L);
        });

        assertNotNull(pacienteResponseActualizado);
        assertEquals("123455777", pacienteResponseActualizado.getDni());

        assertThat(pacienteResponseActualizado)
                .isNotNull()
                .extracting(PacienteResponse::getId,
                        PacienteResponse::getNombre,
                        PacienteResponse::getApellido,
                        PacienteResponse::getDni)
                .containsExactly(1L, "Casse", "Last", "123455777");
    }

    @Test
    @Order(5)
    void deberiaEliminarElPacienteConId() {
        assertDoesNotThrow(() -> {
            pacienteServiceImpl.eliminarPaciente(1L);
        });
        Exception exception = assertThrows(Exception.class, () -> {
            pacienteServiceImpl.buscarPaciente(1L);
        });
        String expectedMessage = "Paciente no encontrado con ID: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Order(6)
    void deberiaLanzarExcepcionAlBuscarPacienteInexistente() {
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class, () -> {
                    long id = 999L;
                    pacienteServiceImpl.buscarPaciente(id);
                });
        String expectedMessage = "Paciente no encontrado con ID: 999";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Order(7)
    void deberiaDevolverUnaListaVaciaDePacientees() {
        List<PacienteResponse> pacientees = pacienteServiceImpl.listarPacientes();
        assertNotNull(pacientees);
        assertTrue(pacientees.isEmpty());
    }


    @Test
    @Order(8)
    void noDeberiaRegistrarUnPacienteConLaMismoDni() {
        DomicilioRequest domicilioRequest = new DomicilioRequest(
                "Calle Falsa", "123", "Springfield", "Illinois"
        );
        PacienteRequest pacienteRequest = new PacienteRequest(
                "Casse", "Last", "123455678", domicilioRequest,
                "casse@gmail.com", "casseLopez", "gshshs",
                LocalDate.of(2026, 1, 28), true, true
        );

        PacienteResponse pacienteResponse = assertDoesNotThrow(() -> {
            return pacienteServiceImpl.guardarPaciente(pacienteRequest);
        });

        assertThat(pacienteResponse)
                .isNotNull()
                .extracting(PacienteResponse::getId,
                        PacienteResponse::getNombre,
                        PacienteResponse::getApellido,
                        PacienteResponse::getDni)
                .containsExactly(2L, "Casse", "Last", "123455678");

        PacienteRequest pacienteRequest1 = new PacienteRequest(
                "Casse", "Last", "123455678", domicilioRequest,
                "casse@gmail.com", "casseLopez", "gshshs",
                LocalDate.of(2026, 1, 28), true, true
        );

        assertThrows(DuplicateDNIException.class, () -> {
            pacienteServiceImpl.guardarPaciente(pacienteRequest1);
        });
    }

}
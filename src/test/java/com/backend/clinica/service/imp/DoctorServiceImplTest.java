package com.backend.clinica.service.imp;

import com.backend.clinica.dto.request.DoctorRequest;
import com.backend.clinica.dto.response.DoctorResponse;
import com.backend.clinica.exception.DuplicateMatriculaException;
import com.backend.clinica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DoctorServiceImplTest {

    @Autowired
    private DoctorServiceImpl doctorServiceImpl;

    @Test
    @Order(1)
    void deberiaCrearDoctor_yRetornarDoctorCreado() {
        DoctorRequest doctorRequest = new DoctorRequest("Casse", "Last", "MAT1234",
                "casse@gmail.com", "casseLopez",
                "gshshs", true, true);

        DoctorResponse doctorResponse = assertDoesNotThrow(() -> {
            return doctorServiceImpl.guardarDoctor(doctorRequest);
        });
        assertNotNull(doctorResponse);
        assertNotNull(doctorResponse.getId());
        assertEquals("Casse", doctorResponse.getNombre());
        assertEquals("Last", doctorResponse.getApellido());
        assertEquals("MAT1234", doctorResponse.getMatricula());
    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDeDoctores() {
        List<DoctorResponse> doctores = doctorServiceImpl.listarDoctores();
        System.out.println(doctores);
        assertNotNull(doctores);
        assertFalse(doctores.isEmpty());
    }


    @Test
    @Order(3)
    void deberiaBuscarDoctorPorId_yRetornarlo() {
        DoctorResponse doctorResponse = doctorServiceImpl.
                buscarDoctor(1L);

        assertThat(doctorResponse)
                .isNotNull()
                .extracting(DoctorResponse::getId,
                        DoctorResponse::getNombre,
                        DoctorResponse::getApellido,
                        DoctorResponse::getMatricula)
                .containsExactly(1L, "Casse", "Last", "MAT1234");

    }

    @Test
    @Order(4)
    void deberiaActualizarLaMatriculaDelDoctorConId() {
        DoctorRequest doctorRequest = new DoctorRequest(
                "Casse", "Last", "MAT1235",
                "casse@gmail.com", "casseLopez",
                "gshshs", true, true);

        DoctorResponse doctorResponseActualizado = assertDoesNotThrow(() -> {
            return doctorServiceImpl.actualizarDoctor(doctorRequest, 1L);
        });

        assertNotNull(doctorResponseActualizado);
        assertEquals("MAT1235", doctorResponseActualizado.getMatricula());

        assertThat(doctorResponseActualizado)
                .isNotNull()
                .extracting(DoctorResponse::getId,
                        DoctorResponse::getNombre,
                        DoctorResponse::getApellido,
                        DoctorResponse::getMatricula)
                .containsExactly(1L, "Casse", "Last", "MAT1235");
    }

    @Test
    @Order(5)
    void deberiaEliminarElDoctorConId() {
        assertDoesNotThrow(() -> {
            doctorServiceImpl.eliminarDoctor(1L);
        });
        Exception exception = assertThrows(Exception.class, () -> {
            doctorServiceImpl.buscarDoctor(1L);
        });
        String expectedMessage = "Doctor no encontrado con ID: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Order(6)
    void deberiaLanzarExcepcionAlBuscarDoctorInexistente() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            long id = 999L;
            doctorServiceImpl.buscarDoctor(id);
        });
        String expectedMessage = "Doctor no encontrado con ID: 999";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Order(7)
    void deberiaDevolverUnaListaVaciaDeDoctores() {
        List<DoctorResponse> doctores = doctorServiceImpl.listarDoctores();
        assertNotNull(doctores);
        assertTrue(doctores.isEmpty());
    }


    @Test
    @Order(8)
    void noDeberiaRegistrarUnDoctorConLaMismaMatricula() {
        DoctorRequest doctorRequest = new DoctorRequest(
                "Casse", "Last", "MAT1234",
                "casse@gmail.com", "casseLopez",
                "gshshs", true, true);

        DoctorResponse doctorResponse = assertDoesNotThrow(() -> {
            return doctorServiceImpl.guardarDoctor(doctorRequest);
        });

        assertThat(doctorResponse)
                .isNotNull()
                .extracting(DoctorResponse::getId,
                        DoctorResponse::getNombre,
                        DoctorResponse::getApellido,
                        DoctorResponse::getMatricula)
                .containsExactly(2L, "Casse", "Last", "MAT1234");

        DoctorRequest doctorRequest1 = new DoctorRequest(
                "Luis", "Last", "MAT1234",
                "luis@gmail.com", "luisLopez",
                "gshshs", true, true);

        assertThrows(DuplicateMatriculaException.class, () -> {
            doctorServiceImpl.guardarDoctor(doctorRequest1);
        });
    }

}
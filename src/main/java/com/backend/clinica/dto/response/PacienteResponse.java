package com.backend.clinica.dto.response;

import com.backend.clinica.dto.request.DomicilioRequest;
import com.backend.clinica.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PacienteResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private DomicilioRequest domicilio;
    private String email;
    private String username;
    private LocalDate fechaIngreso;
    private List<Rol> roles;
    private Boolean activo;
    private Boolean admin;
}

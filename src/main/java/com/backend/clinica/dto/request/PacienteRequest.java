package com.backend.clinica.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PacienteRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;
    @NotBlank(message = "El DNI no puede estar vacío")
    private String dni;
    @Valid
    private DomicilioRequest domicilio;
    @NotBlank
    private String email;
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
    //@NotBlank(message = "La fecha de ingreso no puede estar vacía")
    @FutureOrPresent(message = "La fecha de ingreso no puede ser en el pasado")
    private LocalDate fechaIngreso;
    private Boolean activo;
    private Boolean admin;

}

package com.backend.clinica.dto.response;

import com.backend.clinica.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorResponse {
    private String nombre;
    private String apellido;
    private String matricula;
    private String email;
    private String username;
    private List<Rol> roles;
    private Boolean activo;
    private Boolean admin;
}

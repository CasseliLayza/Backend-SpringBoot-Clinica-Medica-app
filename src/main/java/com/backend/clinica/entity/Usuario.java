package com.backend.clinica.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    private String username;
    private String password;
    private Boolean activo;
    private boolean admin;
    private List<Rol> roles;
}

package com.backend.clinica.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Doctores")
public class Doctor extends Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String nombre;
    @Column(length = 20)
    private String apellido;
    @Column(length = 20)
    private String matricula;
    private String email;
    @Column(length = 50)
    private String username;
    private String password;
    private Boolean activo;
    private Boolean admin;
    @ManyToMany
    @JoinTable(
            name = "Doctor_Rol",
            joinColumns = @JoinColumn(name = "Doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"Doctor_id", "rol_id"})
    )
    private List<Rol> roles;


}

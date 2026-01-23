package com.backend.clinica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pacientes")
public class Paciente extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String nombre;
    @Column(length = 20)
    private String apellido;
    @Column(length = 20)
    private String dni;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;
    @Column(length = 50)
    private String email;
    private String username;
    private String password;
    private LocalDate fechaIngreso;
    @ManyToMany
    @JoinTable(
            name = "Paciente_Rol",
            joinColumns = @JoinColumn(name = "paciente_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"paciente_id", "rol_id"})

    )
    private List<Rol> roles;
    private Boolean activo;
    private Boolean admin;

    public Paciente() {
        this.roles = new ArrayList<>();
    }


}

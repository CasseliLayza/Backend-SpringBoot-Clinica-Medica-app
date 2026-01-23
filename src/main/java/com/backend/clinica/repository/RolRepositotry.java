package com.backend.clinica.repository;

import com.backend.clinica.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepositotry extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);

}

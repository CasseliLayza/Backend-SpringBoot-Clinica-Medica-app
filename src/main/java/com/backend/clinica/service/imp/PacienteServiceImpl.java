package com.backend.clinica.service.imp;

import com.backend.clinica.dto.request.PacienteRequest;
import com.backend.clinica.dto.response.PacienteResponse;
import com.backend.clinica.entity.Domicilio;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.entity.Rol;
import com.backend.clinica.exception.DuplicateDNIException;
import com.backend.clinica.exception.ResourceNotFoundException;
import com.backend.clinica.repository.PacienteRepository;
import com.backend.clinica.repository.RolRepositotry;
import com.backend.clinica.service.PacienteService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RolRepositotry rolRepositotry;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RolRepositotry rolRepositotry) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.rolRepositotry = rolRepositotry;
    }


    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponse> listarPacientes() {
        return pacienteRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PacienteResponse.class))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponse buscarPaciente(Long id) {
        return pacienteRepository.findById(id).map(p -> modelMapper.map(p, PacienteResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public PacienteResponse guardarPaciente(PacienteRequest paciente) {
        pacienteRepository.findByDni(paciente.getDni()).ifPresent(p -> {
            throw new DuplicateDNIException("El DNI ya está registrado: " + paciente.getDni());
        });

        Paciente nuevoPaciente = modelMapper.map(paciente, Paciente.class);
        nuevoPaciente.setPassword(passwordEncoder.encode(paciente.getPassword()));
        nuevoPaciente.setRoles(getRoles(paciente));
        Paciente pacienteGuardado = pacienteRepository.save(nuevoPaciente);

        return modelMapper.map(pacienteGuardado, PacienteResponse.class);

    }

    @Override
    @Transactional
    public PacienteResponse actualizarPaciente(PacienteRequest paciente, Long id) {
        return pacienteRepository.findById(id)
                .map(p -> {
                    p.setNombre(paciente.getNombre());
                    p.setApellido(paciente.getApellido());
                    p.setDni(paciente.getDni());
                    p.setDomicilio(modelMapper.map(paciente.getDomicilio(), Domicilio.class));
                    p.setEmail(paciente.getEmail());
                    p.setUsername(paciente.getUsername());
                    p.setPassword(paciente.getPassword());
                    p.setFechaIngreso(paciente.getFechaIngreso());
                    p.setRoles(getRoles(paciente));
                    p.setActivo(paciente.getActivo());
                    return modelMapper.map(pacienteRepository.save(p), PacienteResponse.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
    }

    private List<Rol> getRoles(PacienteRequest paciente) {
        List<Rol> roles = new ArrayList<>();

        rolRepositotry.findByNombre("ROLE_USER").ifPresent(roles::add);
        if (paciente.getAdmin()) rolRepositotry.findByNombre("ROLE_ADMIN").ifPresent(roles::add);
        return roles;
    }

    @Override
    @Transactional
    public void eliminarPaciente(Long id) {

        if (!pacienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }

        pacienteRepository.deleteById(id);


    }
}

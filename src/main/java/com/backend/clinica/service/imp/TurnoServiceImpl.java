package com.backend.clinica.service.imp;

import com.backend.clinica.dto.request.TurnoRequest;
import com.backend.clinica.dto.response.TurnoResponse;
import com.backend.clinica.entity.Doctor;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.entity.Turno;
import com.backend.clinica.exception.DuplicateTurnoException;
import com.backend.clinica.exception.ResourceNotFoundException;
import com.backend.clinica.repository.DoctorRepository;
import com.backend.clinica.repository.PacienteRepository;
import com.backend.clinica.repository.TurnoRepository;
import com.backend.clinica.service.TurnoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;
    private final DoctorRepository doctorRepository;
    private final PacienteRepository pacienteRepository;
    private final ModelMapper modelMapper;


    public TurnoServiceImpl(TurnoRepository turnoRepository, DoctorRepository doctorRepository, PacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.doctorRepository = doctorRepository;
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<TurnoResponse> listarTurnos() {
        return turnoRepository.findAll().stream()
                .map(t -> modelMapper.map(t, TurnoResponse.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TurnoResponse buscarTurno(Long id) {
        return turnoRepository.findById(id)
                .map(t -> modelMapper.map(t, TurnoResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public TurnoResponse guardarTurno(TurnoRequest turno) {

        turnoRepository.findByFechaHora(turno.getFechaHora()).ifPresent(t -> {
            throw new DuplicateTurnoException("El turno ya está registrado para la fecha y hora: " + turno.getFechaHora());
        });

        Doctor doctor = doctorRepository.findByMatricula(turno.getDoctor().getMatricula())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con Matricula: " + turno.getDoctor().getMatricula()));
        Paciente paciente = pacienteRepository.findByDni(turno.getPaciente().getDni())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con DNI: " + turno.getPaciente().getDni()));

        Turno nuevoTurno = modelMapper.map(turno, Turno.class);
        nuevoTurno.setDoctor(doctor);
        nuevoTurno.setPaciente(paciente);

        return modelMapper.map(turnoRepository.save(nuevoTurno), TurnoResponse.class);


    }

    @Override
    @Transactional
    public TurnoResponse actualizarTurno(TurnoRequest turno, Long id) {
        Turno turnoRecibido = modelMapper.map(turno, Turno.class);
        return turnoRepository.findById(id)
                .map(t -> {
                    t.setFechaHora(turnoRecibido.getFechaHora());
                    t.setPaciente(turnoRecibido.getPaciente());
                    t.setDoctor(turnoRecibido.getDoctor());
                    Turno turnoActualizado = turnoRepository.save(t);
                    return modelMapper.map(turnoActualizado, TurnoResponse.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public void eliminarTurno(Long id) {
        if (!turnoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Turno no encontrado con ID: " + id);
        }

        turnoRepository.deleteById(id);

    }
}

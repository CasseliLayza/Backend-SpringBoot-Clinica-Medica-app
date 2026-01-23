package com.backend.clinica.service.imp;

import com.backend.clinica.dto.request.DoctorRequest;
import com.backend.clinica.dto.response.DoctorResponse;
import com.backend.clinica.entity.Doctor;
import com.backend.clinica.entity.Rol;
import com.backend.clinica.exception.DuplicateMatriculaException;
import com.backend.clinica.exception.ResourceNotFoundException;
import com.backend.clinica.repository.DoctorRepository;
import com.backend.clinica.repository.RolRepositotry;
import com.backend.clinica.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final RolRepositotry rolRepositotry;

    public DoctorServiceImpl(DoctorRepository doctorRepository, ModelMapper modelMapper, RolRepositotry rolRepositotry) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
        this.rolRepositotry = rolRepositotry;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> listarDoctores() {

        return doctorRepository.findAll().stream()
                .map(d -> modelMapper.map(d, DoctorResponse.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse buscarDoctor(Long id) {
        return doctorRepository.findById(id)
                .map(d -> modelMapper.map(d, DoctorResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public DoctorResponse guardarDoctor(DoctorRequest doctor) {
        doctorRepository.findByMatricula(doctor.getMatricula()).ifPresent(d -> {
            throw new DuplicateMatriculaException("La matrícula ya está registrada: " + doctor.getMatricula());
        });

        Doctor nuevoDoctor = modelMapper.map(doctor, Doctor.class);
        //nuevoPaciente.setPassword(passwordEncoder.encode(paciente.getPassword()));
        Doctor doctorGuardado = doctorRepository.save(nuevoDoctor);

        return modelMapper.map(doctorGuardado, DoctorResponse.class);

    }

    @Override
    @Transactional
    public DoctorResponse actualizarDoctor(DoctorRequest doctor, Long id) {
        return doctorRepository.findById(id)
                .map(d -> {
                    d.setNombre(doctor.getNombre());
                    d.setApellido(doctor.getApellido());
                    d.setMatricula(doctor.getMatricula());
                    d.setUsername(doctor.getUsername());
                    d.setPassword(doctor.getPassword());
                    d.setAdmin(doctor.getAdmin());
                    d.setActivo(doctor.getActivo());
                    d.setRoles(getRoles(doctor));
                    return modelMapper.map(doctorRepository.save(d), DoctorResponse.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con ID: " + id));
    }


    private List<Rol> getRoles(DoctorRequest doctor) {
        List<Rol> roles = new ArrayList<>();

        rolRepositotry.findByNombre("ROLE_USER").ifPresent(roles::add);
        if (doctor.getActivo()) rolRepositotry.findByNombre("ROLE_ADMIN").ifPresent(roles::add);
        return roles;
    }

    @Override
    @Transactional
    public void eliminarDoctor(Long id) {
        if (!doctorRepository.existsById(id))
            throw new ResourceNotFoundException("Doctor no encontrado con ID: " + id);
        doctorRepository.deleteById(id);

    }
}

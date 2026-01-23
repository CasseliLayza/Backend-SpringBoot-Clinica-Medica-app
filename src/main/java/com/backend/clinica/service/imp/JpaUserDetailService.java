package com.backend.clinica.service.imp;

import com.backend.clinica.entity.Usuario;
import com.backend.clinica.repository.DoctorRepository;
import com.backend.clinica.repository.PacienteRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailService implements UserDetailsService {

    private final DoctorRepository doctorRepository;
    private final PacienteRepository pacienteRepository;

    public JpaUserDetailService(DoctorRepository doctorRepository, PacienteRepository pacienteRepository) {
        this.doctorRepository = doctorRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Usuario usuario = doctorRepository.findByUsername(username)
                .map(doctor -> (Usuario) doctor)
                .or(() -> pacienteRepository.findByUsername(username)
                        .map(paciente -> (Usuario) paciente))
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado con username: "
                                        + username
                        ));


        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toList());


        return new User(usuario.getUsername(), usuario.getPassword(),
                usuario.getActivo(), true,
                true, true,
                authorities);

    }
}

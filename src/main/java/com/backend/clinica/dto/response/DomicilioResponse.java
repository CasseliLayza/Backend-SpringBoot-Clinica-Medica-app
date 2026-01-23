package com.backend.clinica.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DomicilioResponse {
    private Long id;
    private String calle;
    private String numero;
    private String ciudad;
    private String provincia;
}

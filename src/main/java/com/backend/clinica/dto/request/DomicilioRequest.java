package com.backend.clinica.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DomicilioRequest {
    @NotBlank(message = "La calle no puede estar vacía")
    private String calle;
    @NotBlank(message = "El número no puede estar vacío")
    private String numero;
    @NotBlank(message = "La ciudad no puede estar vacía")
    private String ciudad;
    @NotBlank(message = "La provincia no puede estar vacía")
    private String provincia;

}

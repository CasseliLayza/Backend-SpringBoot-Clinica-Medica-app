package com.backend.clinica.exception;

import jakarta.validation.constraints.NotBlank;

public class DuplicateDNIException extends RuntimeException {
    public DuplicateDNIException(String message) {
        super(message);
    }
}

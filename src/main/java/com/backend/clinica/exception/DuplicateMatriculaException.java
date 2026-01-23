package com.backend.clinica.exception;

public class DuplicateMatriculaException extends RuntimeException {
    public DuplicateMatriculaException(String message) {
        super(message);
    }
}

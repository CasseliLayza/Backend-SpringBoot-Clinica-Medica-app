package com.backend.clinica.exception;

public class DuplicateTurnoException extends RuntimeException {
    public DuplicateTurnoException(String s) {
        super(s);
    }
}

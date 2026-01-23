package com.backend.clinica.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleGranteAuthorityjsonCreator {

    @JsonCreator
    public SimpleGranteAuthorityjsonCreator(
            @JsonProperty("authority") String role) {
    }
}

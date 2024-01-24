package com.infy.gameszone.api.responses;

import java.util.Objects;

public record GenericResponse(String message) {

    public GenericResponse {
        Objects.requireNonNull(message);
    }
}

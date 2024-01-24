package com.infy.gameszone.api.responses;

import java.util.List;
import java.util.Objects;

import com.infy.gameszone.constants.UserStatus;

public record AuthResponse(Integer userId, String userName, String accessToken, List<String> roles, UserStatus status) {

    public AuthResponse {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(userName);
        Objects.requireNonNull(accessToken);
        Objects.requireNonNull(roles);
        Objects.requireNonNull(status);
    }
}

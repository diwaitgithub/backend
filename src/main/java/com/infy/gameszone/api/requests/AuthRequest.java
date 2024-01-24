package com.infy.gameszone.api.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;

public class AuthRequest {

    @NotNull(message = "{user.username.absent}")
    @Size(max = 25, min = 6, message = "{user.username.invalid}")
    @NotEmpty
    private String userName;

    @NotNull(message = "{user.password.absent}")
    @Pattern(regexp = ".*[A-Z]+.*", message = "{user.password.format.uppercase}")
    @Pattern(regexp = ".*[a-z]+.*", message = "{user.password.format.lowercase}")
    @Pattern(regexp = ".*[0-9]+.*", message = "{user.password.format.number}")
    @Pattern(regexp = ".*[^a-zA-Z-0-9].*", message = "{user.password.format.specialcharacter}")
    @Size(max = 25, min = 6, message = "{user.password.format.length}")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

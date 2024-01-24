package com.infy.gameszone.api.requests;

import com.infy.gameszone.constants.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterUserRequest {
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

    @NotNull(message = "{user.email.absent}")
    @Email(message = "{user.email.format.invalid}")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @NotNull(message = "{user.status.absent}")
    // @Pattern(regexp = "^(ACTIVE|INACTIVE|DELETED)$")
    private UserStatus status;

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

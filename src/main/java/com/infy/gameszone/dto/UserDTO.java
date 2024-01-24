package com.infy.gameszone.dto;

import java.util.HashSet;
import java.util.Set;

import com.infy.gameszone.constants.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {

    private Integer userId;

    @NotNull(message = "{user.username.absent}")
    @Size(max = 25, min = 6, message = "{user.username.invalid}")
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

    @NotNull(message = "{user.status.absent}")
    // @Pattern(regexp = "^(ACTIVE|INACTIVE|DELETED)$")
    private UserStatus status;

    private Set<RoleDTO> roles = new HashSet<>();

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDTO other = (UserDTO) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (status != other.status)
            return false;
        if (roles == null) {
            if (other.roles != null)
                return false;
        } else if (!roles.equals(other.roles))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserDTO [userId=" + userId + ", userName=" + userName + ", password=" + password + ", email=" + email
                + ", status=" + status + ", roles=" + roles + "]";
    }

}

package com.infy.gameszone.entity;

import java.util.HashSet;
import java.util.Set;

import com.infy.gameszone.constants.UserStatus;
import com.infy.gameszone.dto.RoleDTO;
import com.infy.gameszone.dto.UserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotNull
    @Column(unique = true)
    private String userName;

    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(columnDefinition = "ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED','DELETED')")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "authorities", joinColumns = {
            @JoinColumn(name = "userId", referencedColumnName = "userId") }, inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "roleId") })
    private Set<Role> roles = new HashSet<>();

    public Integer getUserId() {
        return userId;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public UserDTO toDto() {

        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(this.userId);
        userDTO.setUserName(this.userName);
        userDTO.setPassword(this.password);
        userDTO.setEmail(this.email);
        userDTO.setStatus(status);

        Set<RoleDTO> roleDTOs = new HashSet<RoleDTO>();

        if (roles != null)
            for (Role role : roles) {
                roleDTOs.add(role.toDto());
            }
        userDTO.setRoles(roleDTOs);

        return userDTO;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", email=" + email
                + ", status=" + status + ", roles=" + roles + "]";
    }

}

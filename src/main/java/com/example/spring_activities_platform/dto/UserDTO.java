package com.example.spring_activities_platform.dto;

import com.example.spring_activities_platform.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class UserDTO {
    @Size(min = 10)
    private String email;

    @Size(min = 5)
    private String password;

    @Size(min = 5)
    private String checkPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }
}

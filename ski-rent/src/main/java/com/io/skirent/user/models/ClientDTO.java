package com.io.skirent.user.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class ClientDTO {

    private static final int minLength = 3;

    @NotNull(message="First name cannot be missing or empty")
    @Size(min=minLength, message="First name must not be less than " + minLength + " characters")
    String name;

    @NotNull(message="Last name cannot be missing or empty")
    @Size(min=minLength, message="Last name must not be less than " + minLength + " characters")
    String surname;

    @NotNull
    String birth;

    @Email
    String email;

    @NotNull(message="Password is a required field")
    @Size(min=8, max=32, message="Password must be equal to or greater than 8 characters and less than 32 characters")
    String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

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
}

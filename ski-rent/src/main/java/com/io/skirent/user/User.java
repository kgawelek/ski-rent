package com.io.skirent.user;


import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
abstract class User extends Account {
    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "birthday",
            columnDefinition = "DATE"

    )
    private LocalDate dob;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}

package com.io.skirent.user;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email", columnNames = "email"),
                @UniqueConstraint(name = "user_login", columnNames = "login")
        }
)
public class User {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id",
            nullable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "login",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String login;

    @Column(
            name = "birthday",
            columnDefinition = "DATE"

    )
    private LocalDate dob;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            columnDefinition = "TEXT"
    )
    private String email;

    public User() {

    }

    public User(Long id, String name, String login, LocalDate dob, String email) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.dob = dob;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package be.palit.twikey.jpa.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tw_user")
public class User {

    @Id
    @GeneratedValue
    @Column
    private UUID id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

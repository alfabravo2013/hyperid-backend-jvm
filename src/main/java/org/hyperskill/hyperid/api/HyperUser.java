package org.hyperskill.hyperid.api;

import javax.persistence.*;

@Entity
public class HyperUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String name = "";
    private String surname = "";
    @Column(nullable = false)
    private String password;
    private String accessToken;

    public Long getId() {
        return id;
    }

    public HyperUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HyperUser withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HyperUser withName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public HyperUser withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HyperUser withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public HyperUser withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Override
    public String toString() {
        return "HyperUser{id=" + id + ", username='" + username + "'}";
    }
}

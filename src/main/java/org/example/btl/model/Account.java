package org.example.btl.model;

import jakarta.persistence.*;

import java.sql.Date;

@MappedSuperclass
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Temporal(TemporalType.DATE)
    protected Date birthday;

    protected String name;
    protected String email;

    @Column(unique = true)
    protected String username;
    protected String password;
    protected String gender;

    public Account() {
    }

    public Account(String name, String email, String username, String password, Date birthday, String gender) {
        this.birthday = birthday;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

package org.example.btl.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends Account {

    public User() {}

    public User(String name, String email, String username, String password, Date birthday, String gender) {
        super(name, email, username, password, birthday, gender);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<Borrow> borrows = new HashSet<>();
}

package org.example.btl.libraryManage;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends Account {


    @ManyToMany
    private Set<Document> borrowedDocuments = new HashSet<>();
    public User() {

    }

    public User(String name, String email, String username, String password, Date birthday, String gender) {
        super(name, email, username, password, birthday, gender);
    }
}

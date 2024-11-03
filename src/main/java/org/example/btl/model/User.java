package org.example.btl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends Account {


    @ManyToMany
    private Set<Document> borrowedDocuments = new HashSet<>();

    public User() {}

    public User(String name, String email, String username, String password, Date birthday, String gender) {
        super(name, email, username, password, birthday, gender);
    }

    public User(String name, String email, int id) {
        super(name, email, id);
    }

    public User(int id) {
        this.id = id;
    }

    public Set<Document> getBorrowedDocuments() {
        return borrowedDocuments;
    }

    public void setBorrowedDocuments(Set<Document> borrowedDocuments) {
        this.borrowedDocuments = borrowedDocuments;
    }
}

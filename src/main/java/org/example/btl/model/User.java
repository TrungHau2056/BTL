package org.example.btl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends Account {

    @ManyToMany
    private Set<Document> borrowedDocuments = new HashSet<>();
    public User() {

    }
}

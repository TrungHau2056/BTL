package org.example.btl.libraryManage;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;

    @ManyToMany
    private Set<Author> authors = new HashSet<>();

    @ManyToMany
    private Set<User> borrowers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Document() {

    }

    public Document(String description, String title, Admin admin) {
        this.description = description;
        this.title = title;

        this.admin = admin;
    }

    public Document(String title) {
        this.title = title;
    }

    public Document(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Set<User> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(Set<User> borrowers) {
        this.borrowers = borrowers;
    }

    public int getId() {
        return id;
    }
}

package org.example.btl.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Admin extends Account {
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private Set<Document> addedDocuments = new HashSet<>();

    public Admin() {

    }

    public Admin(String name, String email, String username, String password, Date birthday, String gender) {
        super(name, email, username, password, birthday, gender);
    }

    public Set<Document> getDocuments() {
        return addedDocuments;
    }

    public void setDocuments(Set<Document> documents) {
        this.addedDocuments = documents;
    }

    public void addDocument(Document document) {
        document.setAdmin(this);
        addedDocuments.add(document);
    }
}

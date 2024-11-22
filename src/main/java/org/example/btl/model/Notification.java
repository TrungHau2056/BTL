package org.example.btl.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String message;
    private boolean isRead;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    public Notification(String message) {
        this.message = message;
        isRead = false;
        createdAt = LocalDateTime.now();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setHasRead() {
        isRead = true;
    }
}

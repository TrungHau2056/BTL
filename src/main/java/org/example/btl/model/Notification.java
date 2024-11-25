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

    private String title;
    private String message;
    private boolean isRead;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    public Notification() {
    }

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
        isRead = false;
        createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
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

package org.example.btl.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User extends Account {

    public User() {
    }

    public User(String name, String email, String username, String password, Date birthday, String gender) {
        super(name, email, username, password, birthday, gender);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Borrow> borrows = new HashSet<>();

    @Column(columnDefinition = "BLOB")
    private byte[] avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Notification> notifications = new HashSet<>();

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Set<Borrow> getBorrows() {
        return borrows;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void addBorrow(Borrow borrow) {
        borrows.add(borrow);
        borrow.setUser(this);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
        notification.setUser(this);
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
}

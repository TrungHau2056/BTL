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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Borrow> borrows = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Notification> notifications = new HashSet<>();

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
}

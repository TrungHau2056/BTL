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

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<Notification> notifications = new HashSet<>();

    public void addBorrow(Borrow borrow) {
        borrows.add(borrow);
        borrow.setUser(this);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
        notification.setUser(this);
    }
}

package org.example.btl.service;

import org.example.btl.dao.UserDAO;
import org.example.btl.model.User;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public void save(User item) {
        userDAO.save(item);
    }

    public void update(User item) {
        userDAO.update(item);
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public User findByPassAndUsername(String username, String password) {
        return userDAO.findByPassAndUsername(username, password);
    }

    public String validateRegistration(String name, String email, String username,
                                       String password, String gender, Date birthday) {
        if (Objects.equals(name, "")
                || Objects.equals(email, "")
                || Objects.equals(username, "")
                || Objects.equals(password, "")
                || Objects.equals(gender, "")
                || Objects.equals(birthday, null)) {
            return "Please enter all the information!";
        }
        if (username.length() < 6) {
            return "Your username must be at least 6 characters long.";
        }
        if (password.length() < 8) {
            return "Your password must be at least 8 characters long.";
        }
        User user = findByUsername(username);
        if (user != null) {
            return "This username already exists. Please choose another one.";
        }

        return null;
    }

    public String validateUpdate(String name, String email, String username, String oldUsername,
                                       String password, String gender, Date birthday) {
        if (Objects.equals(name, "")
                || Objects.equals(email, "")
                || Objects.equals(username, "")
                || Objects.equals(password, "")
                || Objects.equals(gender, "")
                || Objects.equals(birthday, null)) {
            return "Please enter all the information!";
        }
        if (username.length() < 6) {
            return "Your username must be at least 6 characters long.";
        }
        if (password.length() < 8) {
            return "Your password must be at least 8 characters long.";
        }
        if (Objects.equals(username, oldUsername)) {
            return null;
        }
        User user = findByUsername(username);
        if (user != null) {
            return "This username already exists. Please choose another one.";
        }

        return null;
    }
}

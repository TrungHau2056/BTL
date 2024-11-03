package org.example.btl.service;

import org.example.btl.dao.UserDAO;
import org.example.btl.model.User;

public class UserService {
    public final UserDAO userDAO = new UserDAO();

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public User findByPassAndUsername(String username, String password) {
        return userDAO.findByPassAndUsername(username, password);
    }

    public void save(User item) {
        userDAO.save(item);
    }
}

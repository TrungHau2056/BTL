package org.example.btl.service;

import org.example.btl.dao.AdminDAO;
import org.example.btl.model.Admin;

import java.sql.Date;
import java.util.Objects;


public class AdminService {
    private AdminDAO adminDAO = new AdminDAO();

    public Admin updateAdmin(Admin admin) {
        return adminDAO.updateAdmin(admin);
    }

    public Admin findByUsername(String username) {
        return adminDAO.findByUsername(username);
    }

    public Admin findByPassAndUsername(String username, String password) {
        return adminDAO.findByPassAndUsername(username, password);
    }

    public void save(Admin item) {
        adminDAO.save(item);
    }

    /**
     * Validates the registration information provided by an admin.
     * @param name The name of the user.
     * @param email The email of the user.
     * @param username The username chosen by the user.
     * @param password The password chosen by the user.
     * @param confirmedPassword The confirmed password to ensure it matches the original password.
     * @param gender The gender of the user.
     * @param birthday The birthday of the user.
     * @return null if the information is valid.
     */
    public String validateRegistration(String name, String email, String username,
                                       String password, String confirmedPassword,
                                       String gender, Date birthday) {
        if (Objects.equals(name, "")
                || Objects.equals(email, "")
                || Objects.equals(username, "")
                || Objects.equals(password, "")
                || Objects.equals(confirmedPassword, "")
                || Objects.equals(gender, "")
                || Objects.equals(birthday, null)) {
            return "Please enter all the information!";
        } else if (!Objects.equals(password, confirmedPassword)) {
            return "Passwords do not match. Please try again.";
        } else if (username.length() < 6) {
            return "Your username must be at least 6 characters long.";
        } else if (password.length() < 8) {
            return "Your password must be at least 8 characters long.";
        } else {
            Admin admin = findByUsername(username);
            if (admin != null) {
                return "This username already exists. Please choose another one.";
            }
        }
        return null;
    }
}

package org.example.btl.service;

import jakarta.persistence.Query;
import org.example.btl.dao.AdminDAO;
import org.example.btl.model.Admin;

import java.util.List;

public class AdminService {
    public final AdminDAO adminDAO = new AdminDAO();

    public Admin findByUsername(String username) {
        return adminDAO.findByUsername(username);
    }

    public Admin findByPassAndUsername(String username, String password) {
        return adminDAO.findByPassAndUsername(username, password);
    }

    public void save(Admin item) {
        adminDAO.save(item);
    }


}

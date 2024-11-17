package org.example.btl.service;

import org.example.btl.dao.AdminDAO;
import org.example.btl.model.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AdminServiceTest {
    @Mock
    AdminDAO adminDAO;

    @InjectMocks
    AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void validateRegistration_emptyField() {
        String validateMess = adminService.validateRegistration("testName", "@fddd",
                "", "",
                "", "", Date.valueOf("2005-11-1"));
        assertEquals("Please enter all the information!", validateMess);
    }

    @Test
    void validateRegistration_nullBirthday() {
        String validateMess = adminService.validateRegistration("testName", "@fddd",
                "goku123456", "012544887",
                "012544887", "male", null);
        assertEquals("Please enter all the information!", validateMess);
    }

    @Test
    void validateRegistration_passNotMatch() {
        String validateMess = adminService.validateRegistration("testName", "@fddd",
                "goku123456", "0125448f87",
                "012544887", "male", Date.valueOf("2005-11-1"));
        assertEquals("Passwords do not match. Please try again.", validateMess);
    }

    @Test
    void validateRegistration_passTooShort() {
        String validateMess = adminService.validateRegistration("testName", "@fddd",
                "goku123456", "01254",
                "01254", "male", Date.valueOf("2005-11-1"));
        assertEquals("Your password must be at least 8 characters long.", validateMess);
    }

    @Test
    void validateRegistration_usernameTooShort() {
        String validateMess = adminService.validateRegistration("testName", "@fddd",
                "goku5", "01254585858",
                "01254585858", "female", Date.valueOf("2005-11-1"));
        assertEquals("Your username must be at least 6 characters long.", validateMess);
    }

    @Test
    void validateRegistration_usernameExist() {
        String username = "krillin01452";
        when(adminDAO.findByUsername(username)).thenReturn(new Admin());

        String validateMess = adminService.validateRegistration("testName", "@fddd",
                username, "01254585858",
                "01254585858", "female", Date.valueOf("2005-11-1"));
        assertEquals("This username already exists. Please choose another one.", validateMess);
    }

    @Test
    void validateRegistration_success() {
        String username = "krillin01452";
        when(adminDAO.findByUsername(username)).thenReturn(null);

        String validateMess = adminService.validateRegistration("testName", "@fddd",
                username, "01254585858",
                "01254585858", "female", Date.valueOf("2005-11-1"));
        assertNull(validateMess);
    }
}
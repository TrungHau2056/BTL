package org.example.btl.service;

import org.example.btl.dao.UserDAO;
import org.example.btl.model.Admin;
import org.example.btl.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    UserDAO userDAO;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void validateRegistration_emptyField() {
        String validateMess = userService.validateRegistration("testName", "@fddd",
                "", "",
                "", Date.valueOf("2005-11-1"));
        assertEquals("Please enter all the information!", validateMess);
    }

    @Test
    void validateRegistration_nullBirthday() {
        String validateMess = userService.validateRegistration("testName", "@fddd",
                "goku123456", "012544887",
                "male", null);
        assertEquals("Please enter all the information!", validateMess);
    }

    @Test
    void validateRegistration_passTooShort() {
        String validateMess = userService.validateRegistration("testName", "@fddd",
                "goku123456", "01254",
                "male", Date.valueOf("2005-11-1"));
        assertEquals("Your password must be at least 8 characters long.", validateMess);
    }

    @Test
    void validateRegistration_usernameTooShort() {
        String validateMess = userService.validateRegistration("testName", "@fddd",
                "goku5", "01254585858",
                "female", Date.valueOf("2005-11-1"));
        assertEquals("Your username must be at least 6 characters long.", validateMess);
    }

    @Test
    void validateRegistration_usernameExist() {
        String username = "krillin01452";
        when(userDAO.findByUsername(username)).thenReturn(new User());

        String validateMess = userService.validateRegistration("testName", "@fddd",
                username, "01254585858",
                "female", Date.valueOf("2005-11-1"));
        assertEquals("This username already exists. Please choose another one.", validateMess);
    }

    @Test
    void validateRegistration_success() {
        String username = "krillin01452";
        when(userDAO.findByUsername(username)).thenReturn(null);

        String validateMess = userService.validateRegistration("testName", "@fddd",
                username, "01254585858",
                "female", Date.valueOf("2005-11-1"));
        assertNull(validateMess);
    }

    @Test
    void validateUpdate() {
    }

    @Test
    void validateUpdate_emptyField() {
        String validateMess = userService.validateUpdate("testName", "@fddd",
                "", "",
                "", "", Date.valueOf("2005-11-1"));
        assertEquals("Please enter all the information!", validateMess);
    }

    @Test
    void validateUpdate_nullBirthday() {
        String validateMess = userService.validateUpdate("testName", "@fddd",
                "goku123456", "gokkkd45", "012544887",
                "male", null);
        assertEquals("Please enter all the information!", validateMess);
    }

    @Test
    void validateUpdate_passTooShort() {
        String validateMess = userService.validateUpdate("testName", "@fddd",
                "goku123456", "gooodke45", "01254",
                "male", Date.valueOf("2005-11-1"));
        assertEquals("Your password must be at least 8 characters long.", validateMess);
    }

    @Test
    void validateUpdate_usernameTooShort() {
        String validateMess = userService.validateUpdate("testName", "@fddd",
                "goku5", "dfghjk12", "01254585858",
                "female", Date.valueOf("2005-11-1"));
        assertEquals("Your username must be at least 6 characters long.", validateMess);
    }

    @Test
    void validateUpdate_usernameExist() {
        String username = "krillin01452";
        when(userDAO.findByUsername(username)).thenReturn(new User());

        String validateMess = userService.validateUpdate("testName", "@fddd",
                username, "12hhhji5", "01254585858",
                "female", Date.valueOf("2005-11-1"));
        assertEquals("This username already exists. Please choose another one.", validateMess);
    }

    @Test
    void validateUpdate_usernameNotChangeSuccess() {
        String username = "krillin01452";
        when(userDAO.findByUsername(username)).thenReturn(new User());

        String validateMess = userService.validateUpdate("testName", "@fddd",
                username, username, "01254585858",
                "female", Date.valueOf("2005-11-1"));
        assertNull(validateMess);
    }

    @Test
    void validateUpdate_success() {
        String username = "krillin01452";
        when(userDAO.findByUsername(username)).thenReturn(null);

        String validateMess = userService.validateUpdate("testName", "@fddd",
                username, "efdgfbgrfe554", "01254585858",
                "female", Date.valueOf("2005-11-1"));
        assertNull(validateMess);
    }
}
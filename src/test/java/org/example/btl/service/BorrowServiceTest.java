package org.example.btl.service;

import org.example.btl.dao.BorrowDAO;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;
import org.example.btl.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowServiceTest {
    @Mock
    BorrowDAO borrowDAO;

    @InjectMocks
    BorrowService borrowService;

    private Document document;
    private User user;
    private Borrow borrow;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        document = mock(Document.class);
        user = mock(User.class);
        borrow = mock(Borrow.class);
    }

    @Test
    void isCurrentlyBorrowing() {
        when(borrowDAO.findByUserCurrentlyBorrowsDocument(user, document)).thenReturn(borrow);

        assertTrue(borrowService.isCurrentlyBorrowing(user, document));
        verify(borrowDAO, times(1)).findByUserCurrentlyBorrowsDocument(user, document);
    }
}
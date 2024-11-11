package org.example.btl.service;

import jakarta.persistence.Query;
import org.example.btl.dao.BorrowDAO;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;
import org.example.btl.model.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class BorrowService {
    private final BorrowDAO borrowDAO = new BorrowDAO();

    public boolean isReturned(Borrow borrow) {
        return borrow.getReturnDate() == null;
    }

    public boolean isCurrentlyBorrowing(User user, Document document) {
        Borrow borrow = findByUserAndDocument(user, document);
        return borrow != null && !isReturned(borrow);
    }

    public Borrow findByUserAndDocument(User user, Document document) {
        return borrowDAO.findByUserAndDocument(user, document);
    }

    public String validateBorrow(User user, Document document) {
        Borrow borrow = findByUserAndDocument(user, document);
        if (borrow != null && !isReturned(borrow)) {
            return "You are currently borrowing this document.";
        }
        if (document.getQuantity() == 0) {
            return "All copies of this document have been borrowed.";
        }
        return null;
    }

    public void borrowDocument(User user, Document document) {
        borrowDAO.borrowDocument(user, document);
    }


    public void returnDocument(User user, Document document) {
        borrowDAO.returnDocument(user, document);
    }
}

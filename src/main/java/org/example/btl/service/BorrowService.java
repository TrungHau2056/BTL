package org.example.btl.service;

import org.example.btl.dao.BorrowDAO;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;
import org.example.btl.model.User;

public class BorrowService {
    private BorrowDAO borrowDAO = new BorrowDAO();

    public boolean isCurrentlyBorrowing(User user, Document document) {
        return borrowDAO.findByUserCurrentlyBorrowsDocument(user, document) != null;
    }

    public Borrow findByUserCurrentlyBorrowsDocument(User user, Document document) {
        return borrowDAO.findByUserCurrentlyBorrowsDocument(user, document);
    }

    public String validateBorrow(User user, Document document) {
        if (isCurrentlyBorrowing(user, document)) {
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

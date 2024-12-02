package org.example.btl.service;

import org.example.btl.dao.BorrowDAO;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;
import org.example.btl.model.User;

import java.util.List;

public class BorrowService {
    private BorrowDAO borrowDAO = new BorrowDAO();

    public boolean isCurrentlyBorrowing(User user, Document document) {
        return borrowDAO.findByUserCurrentlyBorrowsDocument(user, document) != null;
    }

    public Borrow findByUserCurrentlyBorrowsDocument(User user, Document document) {
        return borrowDAO.findByUserCurrentlyBorrowsDocument(user, document);
    }

    public List<Borrow> findDocHasReturned(User user) {
        return borrowDAO.findDocHasReturned(user);
    }

    /**
     * validates whether a user can borrow a document.
     *
     * @param user
     * @param document
     * @return null if the user can borrow the document.
     */
    public String validateBorrow(User user, Document document) {
        if (isCurrentlyBorrowing(user, document)) {
            return "You are currently borrowing this document.";
        }
        if (document.getQuantity() == 0) {
            return "All copies of this document have been borrowed.";
        }
        return null;
    }

    public User borrowDocument(User user, Document document) {
        return borrowDAO.borrowDocument(user, document);
    }

    public User returnDocument(User user, Document document) {
        return borrowDAO.returnDocument(user, document);
    }
}

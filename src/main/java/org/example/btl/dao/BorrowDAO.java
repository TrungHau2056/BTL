package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;
import org.example.btl.model.HibernateUtils;
import org.example.btl.model.User;
import org.hibernate.Session;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class BorrowDAO {
    private Session session;

    public Borrow findByUserCurrentlyBorrowsDocument(User user, Document document) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Borrow WHERE document =: document AND user =: user  AND returnDate IS NULL");
        query.setParameter("user", user);
        query.setParameter("document", document);
        List<Borrow> borrows = query.getResultList();

        session.close();
        if (borrows.isEmpty()) {
            return null;
        }
        return borrows.getFirst();
    }

    public List<Borrow> findDocHasReturned(User user) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Borrow WHERE user = :user AND returnDate IS NOT NULL ORDER BY returnDate DESC");
        query.setParameter("user", user);
        List<Borrow> borrows = query.getResultList();

        session.close();
        return borrows;
    }

    public List<Document> findDocCurrentBorrow(User user) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("SELECT document FROM Borrow WHERE user = :user AND returnDate IS NULL");
        query.setParameter("user", user);
        List<Document> documents = query.getResultList();

        session.close();
        return documents;
    }

    public void borrowDocument(User user, Document document) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Borrow borrow = new Borrow();
        user = session.merge(user);
        user.addBorrow(borrow);
        document = session.merge(document);
        document.decreaseQuantity();
        document.addBorrow(borrow);

        session.persist(borrow);

        session.getTransaction().commit();
        session.close();
    }

    public void returnDocument(User user, Document document) {
        Borrow borrow = findByUserCurrentlyBorrowsDocument(user, document);

        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        document.increaseQuantity();
        borrow.setReturnDate(Date.valueOf(LocalDate.now()));
        session.merge(document);
        session.merge(borrow);

        session.getTransaction().commit();
        session.close();
    }
}

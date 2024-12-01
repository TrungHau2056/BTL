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
import java.util.stream.Collectors;

public class BorrowDAO {
    private Session session;

    public Borrow findByUserCurrentlyBorrowsDocument(User user, Document document) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Borrow WHERE document =: document AND user =: user AND returnDate IS NULL");
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

        Query query = session.createQuery("FROM Borrow WHERE user = :user AND returnDate IS NOT NULL");
        query.setParameter("user", user);
        List<Borrow> borrows = query.getResultList();

        session.close();
        return borrows;
    }

    public List<Document> findDocCurrentlyBorrow(User user) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("SELECT document FROM Borrow WHERE user = :user AND returnDate IS NULL");
        query.setParameter("user", user);
        List<Document> documents = query.getResultList();

        session.close();
        return documents;
    }

    public List<User> findUserCurrentlyBorrow(Document document) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("SELECT user FROM Borrow WHERE document = :document AND returnDate IS NOT NULL");
        query.setParameter("document", document);
        List<User> users = query.getResultList();

        session.close();
        return users;
    }

    public User borrowDocument(User user, Document document) {
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

        return user;
    }

    public User returnDocument(User user, Document document) {
        Borrow borrow = findByUserCurrentlyBorrowsDocument(user, document);

        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        borrow.getDocument().increaseQuantity();
        borrow.setReturnDate(Date.valueOf(LocalDate.now()));

        session.merge(borrow);

        session.getTransaction().commit();
        session.close();

        return borrow.getUser();
    }
}

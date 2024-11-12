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

    public Borrow findByUserAndDocument(User user, Document document) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Borrow WHERE document =: document AND user =: user");
        query.setParameter("user", user);
        query.setParameter("document", document);
        List<Borrow> borrows = query.getResultList();

        session.close();
        if (borrows.isEmpty()) return null;
        return borrows.getFirst();
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
        Borrow borrow = findByUserAndDocument(user, document);

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

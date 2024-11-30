package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.*;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

public class UserDAO implements BaseDAO<User> {
    private Session session;

    @Override
    public void save(User item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(User item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(User item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> findAll() {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User", User.class);
        List<User> users = query.getResultList();

        session.close();
        return users;
    }

    public User findByUsername(String username) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User WHERE username = :username");
        query.setParameter("username", username);
        List<User> users = query.getResultList();

        session.close();
        if (users.isEmpty()) return null;
        else return users.getFirst();
    }

    public User findByPassAndUsername(String username, String password) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User WHERE username = :username and password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> users = query.getResultList();

        session.close();
        if (users.isEmpty()) return null;
        else return users.getFirst();
    }

    public Set<Borrow> getBorrows(User user) {
        Set<Borrow> borrows;
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        user = session.merge(user);
        borrows = user.getBorrows();

        session.close();
        return borrows;
    }

    public Set<Notification> getNotification(User user) {
        Set<Notification> notifications;
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        user = session.merge(user);
        notifications = user.getNotifications();

        session.close();
        return notifications;
    }
}

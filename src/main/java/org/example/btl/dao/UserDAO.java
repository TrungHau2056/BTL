package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.*;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDAO {
    private Session session;

    /**
     * save new user.
     *
     * @param item
     */
    public void save(User item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(item);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * update a user.
     *
     * @param item
     */
    public void update(User item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(item);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * delete a user.
     *
     * @param item
     */
    public void delete(User item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(item);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * find all the users in the database.
     *
     * @return
     */
    public List<User> findAll() {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM User", User.class);
        List<User> users = query.getResultList();

        session.close();
        return users;
    }

    /**
     * testing purpose.
     *
     * @param username
     * @return
     */
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

    /**
     * find with pass and username.
     *
     * @param username
     * @param password
     * @return
     */
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

    /**
     * get all the borrows of a user.
     *
     * @param user
     * @return
     */
    public List<Borrow> getBorrows(User user) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Borrow WHERE user = :user");
        query.setParameter("user", user);
        List<Borrow> borrows = query.getResultList();

        session.close();
        return borrows;
    }

    /**
     * get all the notis of an user.
     *
     * @param user
     * @return
     */
    public List<Notification> getNotification(User user) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Notification WHERE user = :user");
        query.setParameter("user", user);
        List<Notification> notifications = query.getResultList();

        session.close();
        return notifications;
    }
}

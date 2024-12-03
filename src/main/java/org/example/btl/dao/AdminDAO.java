package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.Admin;
import org.example.btl.model.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class AdminDAO {
    private Session session;

    /**
     * save an admin.
     *
     * @param item
     */
    public void save(Admin item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(item);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * testing purpose.
     *
     * @param username
     * @return
     */
    public Admin findByUsername(String username) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Admin WHERE username = :username");
        query.setParameter("username", username);
        List<Admin> admins = query.getResultList();

        session.close();
        if (admins.isEmpty()) return null;
        else return admins.getFirst();
    }

    /**
     * find with pass and username for validate purpose.
     *
     * @param username
     * @param password
     * @return
     */
    public Admin findByPassAndUsername(String username, String password) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Admin WHERE username = :username and password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<Admin> admins = query.getResultList();

        session.close();
        if (admins.isEmpty()) return null;
        else return admins.getFirst();
    }

    /**
     * update an admin for change pass func.
     *
     * @param item
     * @return
     */
    public Admin updateAdmin(Admin item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        item = session.merge(item);
        session.getTransaction().commit();
        session.close();

        return item;
    }
}

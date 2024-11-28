package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.Admin;
import org.example.btl.model.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class AdminDAO implements BaseDAO<Admin> {
    private Session session;

    @Override
    public void save(Admin item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Admin item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Admin item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Admin> findAll() {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Admin", Admin.class);
        List<Admin> admins = query.getResultList();

        session.close();
        return admins;
    }

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
}

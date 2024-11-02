package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.Admin;
import org.example.btl.model.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class AdminDAO implements BaseDAO<Admin>{
    private Session session = HibernateUtils.getSessionFactory().openSession();

    @Override
    public void save(Admin item) {
        session.beginTransaction();
        session.persist(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Admin item) {
        session.beginTransaction();
        session.merge(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Admin item) {
        session.beginTransaction();
        session.remove(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Admin> findAll() {
        session.beginTransaction();
        Query query = session.createQuery("FROM Admin", Admin.class);
        List<Admin> admins = query.getResultList();
        session.close();
        return admins;
    }

    @Override
    public Admin findById(int id) {
        session.beginTransaction();
        Admin admin = session.get(Admin.class, id);
        session.close();
        return admin;
    }

    public Admin findByUsername(String username) {
        session.beginTransaction();
        Query query = session.createQuery("FROM Admin WHERE username = :username");
        query.setParameter("username", username);
        List<Admin> admins = query.getResultList();
        session.close();
        if (admins.isEmpty()) return null;
        else return admins.get(0);
    }
}

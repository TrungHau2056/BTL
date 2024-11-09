package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.Admin;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class AuthorDAO implements BaseDAO<Author>{
    private Session session;

    @Override
    public void save(Author item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Author item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Author item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Author> findAll() {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Document", Admin.class);
        List<Author> authors = query.getResultList();
        session.close();
        return authors;
    }

    @Override
    public Author findById(int id) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Author author = session.get(Author.class, id);
        session.close();
        return author;
    }
}

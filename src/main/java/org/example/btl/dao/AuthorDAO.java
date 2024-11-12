package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.Author;
import org.example.btl.model.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

public class AuthorDAO {
    private Session session;

    public Author findByName(String name) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Author WHERE name = :name");
        query.setParameter("name", name);
        List<Author> authors = query.getResultList();

        session.close();
        if (authors.isEmpty()) return null;
        else return authors.getFirst();
    }
}
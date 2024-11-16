package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.Author;
import org.example.btl.model.HibernateUtils;
import org.example.btl.model.Publisher;
import org.hibernate.Session;

import java.util.List;

public class PublisherDAO {
    private Session session;

    public Publisher findByName(String name) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Publisher WHERE name = :name");
        query.setParameter("name", name);
        List<Publisher> publishers = query.getResultList();

        session.close();
        if (publishers.isEmpty()) return null;
        else return publishers.getFirst();
    }

    public List<Publisher> findByKeyword(String keyword) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Publisher WHERE name LIKE :keyword");
        query.setParameter("keyword", "%" + keyword + "%");
        List<Publisher> publishers = query.getResultList();

        session.close();
        return publishers;
    }
}

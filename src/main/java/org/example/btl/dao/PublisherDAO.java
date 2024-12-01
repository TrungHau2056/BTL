package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.HibernateUtils;
import org.example.btl.model.Publisher;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Set<Document> getDocuments(Publisher publisher) {
        Set<Document> documents;
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        publisher = session.merge(publisher);
        documents = new HashSet<>(publisher.getDocuments());

        session.close();
        return documents;
    }
}

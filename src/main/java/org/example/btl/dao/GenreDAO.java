package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;
import org.example.btl.model.HibernateUtils;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenreDAO {
    private Session session;

    public void save(Genre item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(item);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Mostly used for testing purpose.
     *
     * @param name
     * @return
     */
    public Genre findByName(String name) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Genre WHERE name = :name");
        query.setParameter("name", name);
        List<Genre> genres = query.getResultList();

        session.close();
        if (genres.isEmpty()) return null;
        else return genres.getFirst();
    }

    /**
     * search by genre name.
     *
     * @param keyword
     * @return a list of genres.
     */
    public List<Genre> findByKeyword(String keyword) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Genre WHERE name LIKE :keyword");
        query.setParameter("keyword", "%" + keyword + "%");
        List<Genre> genres = query.getResultList();

        session.close();
        return genres;
    }

    /**
     * find all the doc with this genre.
     *
     * @param genre
     * @return
     */
    public Set<Document> getDocuments(Genre genre) {
        Set<Document> documents;
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        genre = session.merge(genre);
        documents = new HashSet<>(genre.getDocuments());

        session.close();
        return documents;
    }
}

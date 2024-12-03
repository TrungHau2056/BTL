package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.HibernateUtils;
import org.hibernate.Session;

import javax.print.Doc;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthorDAO {
    private Session session;

    /**
     * testing purpose.
     *
     * @param item
     * @return
     */
    public void save(Author item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(item);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * testing purpose.
     *
     * @param name
     * @return
     */
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

    /**
     * search all the authors by keyword.
     *
     * @param keyword
     * @return
     */
    public List<Author> findByKeyword(String keyword) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Author WHERE name LIKE :keyword");
        query.setParameter("keyword", "%" + keyword + "%");
        List<Author> authors = query.getResultList();

        session.close();
        return authors;
    }

    /**
     * get all the doc of an author.
     *
     * @param author
     * @return
     */
    public Set<Document> getDocuments(Author author) {
        Set<Document> documents;
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        author = session.merge(author);
        documents = new HashSet<>(author.getDocuments());

        session.close();
        return documents;
    }
}

package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.*;
import org.hibernate.Session;

import java.util.List;

public class DocumentDAO implements BaseDAO<Document> {
    private Session session;
    private PublisherDAO publisherDAO = new PublisherDAO();
    private AuthorDAO authorDAO = new AuthorDAO();
    private GenreDAO genreDAO = new GenreDAO();

    @Override
    public void save(Document item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Document item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Document item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Document> findAll() {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Document", Admin.class);
        List<Document> documents = query.getResultList();
        session.close();
        return documents;
    }

    @Override
    public Document findById(int id) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Document document = session.get(Document.class, id);
        session.close();
        return document;
    }

    public Document findByTitle(String title) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Document WHERE title = :title");
        query.setParameter("title", title);
        List<Document> documents = query.getResultList();

        session.close();
        if (documents.isEmpty()) return null;
        else return documents.getFirst();
    }

    public List<Document> searchByTitleKeyword(String keyword) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Document WHERE title LIKE :keyword");
        query.setParameter("keyword", "%" + keyword + "%");
        List<Document> documents = query.getResultList();

        session.close();
        return documents;
    }

    public void saveWithAdminAuthorsPublisherGenre(Document document, Admin admin,
                                                   List<String> authorNames, String publisherName,
                                                   List<String> genreNames) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        admin = session.merge(admin);
        admin.addDocument(document);

        Publisher publisher = publisherDAO.findByName(publisherName);
        if (publisher == null) {
            publisher = new Publisher(publisherName);
        } else publisher = session.merge(publisher);
        publisher.addDocument(document);

        for (String authorName : authorNames) {
            Author author = authorDAO.findByName(authorName);
            if (author == null) {
                author = new Author(authorName);
            } else author = session.merge(author);
            author.addDocument(document);
        }

        for (String genreName : genreNames) {
            Genre genre = genreDAO.findByName(genreName);
            if (genre == null) {
                genre = new Genre(genreName);
            } else genre = session.merge(genre);
            genre.addDocument(document);
        }

        session.persist(document);
        session.getTransaction().commit();
        session.close();
    }
}

package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.*;
import org.hibernate.Session;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
        Query query = session.createQuery("FROM Document", Document.class);
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

    public List<Document> searchByTitleBorrowed(User user, String keyword) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("SELECT document FROM Borrow " +
                "WHERE user = :user " +
                "AND returnDate IS NULL " +
                "AND document.title LIKE :keyword");
        query.setParameter("user", user);
        query.setParameter("keyword", "%" + keyword + "%");
        List<Document> documents = query.getResultList();

        session.close();
        return documents;
    }

    public List<Document> searchByTitleNotBorrowed(User user, String keyword) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Document d WHERE d.title LIKE :keyword" +
                " AND (" +
                " d NOT IN (" +
                "        SELECT document FROM Borrow " +
                "        WHERE user = :user AND returnDate IS NULL" +
                "          )" +
                "  OR d IN (" +
                "        SELECT document FROM Borrow " +
                "        WHERE user = :user AND returnDate IS NOT NULL" +
                "          )" +
                ")");

        query.setParameter("user", user);
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

        if (!Objects.equals(publisherName, "")) {
            Publisher publisher = publisherDAO.findByName(publisherName);
            if (publisher == null) {
                publisher = new Publisher(publisherName);
            } else publisher = session.merge(publisher);
            publisher.addDocument(document);
        }

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

    public Document deleteAuthorGenre(Document document) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        document = session.merge(document);
        Set<Author> oldAuthors = new HashSet<>(document.getAuthors());
        Set<Genre> oldGenres = new HashSet<>(document.getGenres());

        for (Author author : oldAuthors) {
            author = session.merge(author);
            author.deleteDocument(document);
        }

        for (Genre genre : oldGenres) {
            genre = session.merge(genre);
            genre.deleteDocument(document);
        }

        session.merge(document);
        session.getTransaction().commit();
        session.close();

        return document;
    }

    public void updateDocument(Document document,
                               List<String> authorNames, String publisherName,
                               List<String> genreNames) {
        document = deleteAuthorGenre(document);

        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        if (!Objects.equals(publisherName, "")
                && (document.getPublisher() == null
                || !Objects.equals(publisherName, document.getPublisher().getName()))) {
            Publisher publisher = publisherDAO.findByName(publisherName);
            if (publisher == null) {
                publisher = new Publisher(publisherName);
            }
            publisher.addDocument(document);
        }

        for (String authorName : authorNames) {
            Author author = authorDAO.findByName(authorName);
            if (author == null) {
                author = new Author(authorName);
            }
            author.addDocument(document);
        }

        for (String genreName : genreNames) {
            Genre genre = genreDAO.findByName(genreName);
            if (genre == null) {
                genre = new Genre(genreName);
            }
            genre.addDocument(document);


            session.merge(document);
            session.getTransaction().commit();
            session.close();
        }
    }

    public void deleteDocument(Document document) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        for (Borrow borrow : document.getBorrows()) {
            borrow.setReturnDate(Date.valueOf(LocalDate.now()));
            borrow.setDocument(null);
            session.merge(borrow);
        }

        document = session.merge(document);
        session.remove(document);

        session.getTransaction().commit();
        session.close();
    }
}

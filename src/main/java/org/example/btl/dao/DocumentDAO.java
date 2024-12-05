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

public class DocumentDAO {
    private Session session;
    private PublisherDAO publisherDAO = new PublisherDAO();
    private AuthorDAO authorDAO = new AuthorDAO();
    private GenreDAO genreDAO = new GenreDAO();

    /**
     * uses for update added-by-ISBN docs.
     *
     * @param item
     */
    public void update(Document item) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(item);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * find all the docs.
     *
     * @return
     */
    public List<Document> findAll() {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Document", Document.class);
        List<Document> documents = query.getResultList();
        session.close();
        return documents;
    }

    /**
     * Mostly used for testing purpose.
     *
     * @param title
     * @return
     */
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

    /**
     * search by title.
     *
     * @param keyword
     * @return a list of doc.
     */
    public List<Document> searchByTitleKeyword(String keyword) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Document WHERE title LIKE :keyword");
        query.setParameter("keyword", "%" + keyword + "%");
        List<Document> documents = query.getResultList();

        session.close();
        return documents;
    }

    /**
     * search by title but the status is borrowed.
     *
     * @param keyword
     * @return a list of doc.
     */
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

    /**
     * search by title but the status is not borrowed.
     *
     * @param keyword
     * @return a list of doc.
     */
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

    /**
     * save the doc.
     *
     * @param document
     * @param admin
     * @param authorNames
     * @param publisherName
     * @param genreNames
     */
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
                session.persist(publisher);
            } else {
                publisher = session.merge(publisher);
            }
            publisher.addDocument(document);
        }

        for (String authorName : authorNames) {
            Author author = authorDAO.findByName(authorName);
            if (author == null) {
                author = new Author(authorName);
                session.persist(author);
            } else {
                author = session.merge(author);
            }
            author.addDocument(document);
        }

        for (String genreName : genreNames) {
            Genre genre = genreDAO.findByName(genreName);
            if (genre == null) {
                genre = new Genre(genreName);
                session.persist(genre);
            } else {
                genre = session.merge(genre);
            }
            genre.addDocument(document);
        }

        session.persist(document);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * delete all the authors and genres for update purpose.
     *
     * @param document
     * @return
     */
    public Document deleteAuthorGenre(Document document) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        document.getAuthors().clear();
        document.getGenres().clear();
        document = session.merge(document);

        session.getTransaction().commit();
        session.close();

        return document;
    }

    /**
     * update a doc.
     *
     * @param document
     * @param authorNames
     * @param publisherName
     * @param genreNames
     * @return an updated doc.
     */
    public Document updateDocument(Document document,
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
                session.persist(publisher);
            } else {
                publisher = session.merge(publisher);
            }
            publisher.addDocument(document);
        }

        for (String authorName : authorNames) {
            Author author = authorDAO.findByName(authorName);
            if (author == null) {
                author = new Author(authorName);
                session.persist(author);
            } else {
                author = session.merge(author);
            }
            author.addDocument(document);
        }

        for (String genreName : genreNames) {
            Genre genre = genreDAO.findByName(genreName);
            if (genre == null) {
                genre = new Genre(genreName);
                session.persist(genre);
            } else {
                genre = session.merge(genre);
            }
            genre.addDocument(document);
        }
        document = session.merge(document);
        session.getTransaction().commit();
        session.close();

        return document;
    }

    /**
     * delete a doc.
     *
     * @param document
     */
    public void deleteDocument(Document document) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        document = session.merge(document);
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

    /**
     * if someone is borrowing this doc.
     *
     * @param document
     * @return true if someone is borrowing this doc.
     */
    public boolean isCurrentlyBorrow(Document document) {
        boolean isBorrow = false;
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        document = session.merge(document);
        for (Borrow borrow : document.getBorrows()) {
            if (borrow.getReturnDate() == null) {
                isBorrow = true;
                break;
            }
        }

        session.close();
        return isBorrow;
    }

    /**
     * find doc added by an admin.
     *
     * @param admin
     * @return a list of doc.
     */
    public List<Document> findDocAddedByAdmin(Admin admin) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Document WHERE admin = :admin");
        query.setParameter("admin", admin);
        List<Document> documents = query.getResultList();

        session.close();
        return documents;
    }

    public List<Rating> getRating(Document document) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Rating WHERE document = :document");
        query.setParameter("document", document);
        List<Rating> ratings = query.getResultList();

        session.close();
        return ratings;
    }
}

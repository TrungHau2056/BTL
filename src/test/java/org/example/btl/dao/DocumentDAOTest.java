package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DocumentDAOTest {
    private static SessionFactory sessionFactory;
    private Session session;

    private DocumentDAO documentDAO = new DocumentDAO();
    private AdminDAO adminDAO = new AdminDAO();
    private AuthorDAO authorDAO = new AuthorDAO();
    private GenreDAO genreDAO = new GenreDAO();

    @BeforeAll
    static void config() {
        sessionFactory = new Configuration()
                .configure("hibernate_test.cfg.xml")
                .buildSessionFactory();
        MockedStatic<HibernateUtils> hibernateUtilMock = Mockito.mockStatic(HibernateUtils.class);
        hibernateUtilMock.when(HibernateUtils::getSessionFactory).thenReturn(sessionFactory);
    }

    @Test
    void saveDocTest() {
        Admin admin = new Admin("name", "@mjfd", "goku234rt",
                 "0123rethfg", Date.valueOf("2005-05-05"), "Male");
        adminDAO.save(admin);
        authorDAO.save(new Author("AK"));
        genreDAO.save(new Genre("action"));


        Document document = new Document("dbz", "goood manga", 10, "", true);
        documentDAO.saveWithAdminAuthorsPublisherGenre(document, admin,
                List.of("AK", "Boichi"), "Kd", List.of("action", "fantasy"));

        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Author WHERE name = :name");
        query.setParameter("name", "AK");
        List<Author> authors = query.getResultList();

        query = session.createQuery("FROM Genre WHERE name = :name");
        query.setParameter("name", "action");
        List<Genre> genres = query.getResultList();

        session.close();

        admin = adminDAO.findByUsername("goku234rt");
        assertEquals("0123rethfg", admin.getPassword());

        assertEquals(1, authors.size());
        assertEquals(1, genres.size());
        assertEquals(2, document.getAuthors().size());
        assertEquals(2, document.getGenres().size());
    }

    @Test
    void deleteAuthorGenreTest() {
        Document document = documentDAO.findByTitle("dbz");
        document = documentDAO.deleteAuthorGenre(document);

        Author author = authorDAO.findByName("AK");
        Author author1 = authorDAO.findByName("Boichi");

        Genre genre = genreDAO.findByName("action");
        Genre genre1 = genreDAO.findByName("fantasy");

        Set<Document> documents  = authorDAO.getDocuments(author);
        Set<Document> documents1  = authorDAO.getDocuments(author1);
        Set<Document> documents2 = genreDAO.getDocuments(genre);
        Set<Document> documents3 = genreDAO.getDocuments(genre1);

        assertFalse(documents.contains(document));
        assertFalse(documents1.contains(document));
        assertFalse(documents2.contains(document));
        assertFalse(documents3.contains(document));
        assertTrue(document.getAuthors().isEmpty());
        assertTrue(document.getGenres().isEmpty());
    }

    @Test
    void updateDocTest() {
        Admin admin = adminDAO.findByUsername("goku234rt");
        authorDAO.save(new Author("BCD"));

        Document document = new Document("dbfdz", "gooodss manga", 10, "", false);
        documentDAO.saveWithAdminAuthorsPublisherGenre(document, admin,
                List.of("AK", "Boichi"), "Kd", List.of("action", "fantasy"));

        document = documentDAO.updateDocument(document, List.of("AK", "Boichi", "abcd"), "newPublisher", List.of("action", "fantasy", "comedy"));


        List<String> authors = new ArrayList<>();
        for (Author author : document.getAuthors()) {
            authors.add(author.getName());
        }

        List<String> genres = new ArrayList<>();
        for (Genre genre : document.getGenres()) {
            genres.add(genre.getName());
        }

        assertEquals(3, document.getAuthors().size());

        assertTrue(authors.contains("Boichi"));
        assertTrue(authors.contains("AK"));
        assertTrue(authors.contains("abcd"));
        assertTrue(genres.contains("fantasy"));
        assertTrue(genres.contains("action"));
        assertEquals("newPublisher", document.getPublisher().getName());
    }
}
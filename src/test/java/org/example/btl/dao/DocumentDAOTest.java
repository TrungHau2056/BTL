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
import java.util.List;

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

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveDocTest() {
        Admin admin = new Admin("name", "@mjfd", "goku234rt",
                 "0123rethfg", Date.valueOf("2005-05-05"), "Male");
        adminDAO.save(admin);
        authorDAO.save(new Author("AK"));
        genreDAO.save(new Genre("action"));


        Document document = new Document("dbz", "goood manga", 10, "");
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
}
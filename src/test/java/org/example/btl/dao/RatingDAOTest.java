package org.example.btl.dao;

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

class RatingDAOTest {
    private static SessionFactory sessionFactory;
    private Session session;

    private RatingDAO ratingDAO = new RatingDAO();
    private DocumentDAO documentDAO = new DocumentDAO();
    private AdminDAO adminDAO = new AdminDAO();
    private UserDAO userDAO = new UserDAO();

    @BeforeAll
    static void config() {
        sessionFactory = new Configuration()
                .configure("hibernate_test.cfg.xml")
                .buildSessionFactory();
        MockedStatic<HibernateUtils> hibernateUtilMock = Mockito.mockStatic(HibernateUtils.class);
        hibernateUtilMock.when(HibernateUtils::getSessionFactory).thenReturn(sessionFactory);
    }

    @Test
    void updateOrAddRating() {
        Admin admin = new Admin("name", "@mjfd", "goku234rt",
                "0123rethfg", Date.valueOf("2005-05-05"), "Male");
        adminDAO.save(admin);
        User user = new User("name", "@mjfd", "goku234rt",
                "0123rethfg", Date.valueOf("2005-05-05"), "Male");
        userDAO.save(user);

        Document document = new Document("dbz", "goood manga", 10, "", true);
        documentDAO.saveWithAdminAuthorsPublisherGenre(document, admin,
                List.of("AK", "Boichi"), "Kd", List.of("action", "fantasy"));

        ratingDAO.updateOrAddRating(user, document, 2);

        assertEquals(1, documentDAO.getRating(document).size());
        assertEquals(2, documentDAO.getRating(document).getFirst().getScore());

        ratingDAO.updateOrAddRating(user, document, 5);
        assertEquals(5, ratingDAO.getUserRatingOnDoc(user, document).getScore());

        ratingDAO.updateOrAddRating(user, document, 3);
        assertEquals(3, documentDAO.getRating(document).getFirst().getScore());
    }

    @Test
    void getUserRatingOnDoc() {
        User user = userDAO.findByUsername("goku234rt");
        Document document = documentDAO.findByTitle("dbz");

        Rating rating = ratingDAO.getUserRatingOnDoc(user, document);
        assertEquals(3, rating.getScore());
    }
}
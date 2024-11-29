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

class BorrowDAOTest {
    private static SessionFactory sessionFactory;

    BorrowDAO borrowDAO = new BorrowDAO();
    UserDAO userDAO = new UserDAO();
    AdminDAO adminDAO = new AdminDAO();
    DocumentDAO documentDAO = new DocumentDAO();

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
    void borrowDocument() {
        User user = new User("name", "@mjfd", "goku234rt",
                "0123rethfg", Date.valueOf("2005-05-05"), "Male");
        userDAO.save(user);

        Admin admin = new Admin("nagfe", "@mfdd", "gdd8dft",
                "0123ffg", Date.valueOf("2005-05-05"), "Male");
        adminDAO.save(admin);

        Document document = new Document("dbfdz", "gooodss manga", 10, "", true);
        documentDAO.saveWithAdminAuthorsPublisherGenre(document, admin,
                List.of("AK", "Boichi"), "Kd", List.of("action", "fantasy"));

        borrowDAO.borrowDocument(user, document);

        Borrow borrow = borrowDAO.findByUserCurrentlyBorrowsDocument(user, document);

        assertEquals("dbfdz", borrow.getDocument().getTitle());
        assertEquals("name", borrow.getUser().getName());
        assertEquals(9, documentDAO.findByTitle("dbfdz").getQuantity());
    }

    @Test
    void returnDocument() {
        User user = new User("nrrame", "@mjrfd", "gokurrs234rt",
                "0123resthfg", Date.valueOf("2005-05-05"), "Male");
        userDAO.save(user);

        Admin admin = new Admin("nagrrfe", "@mfsdd", "gdd8ssdft",
                "0123ffsg", Date.valueOf("2005-05-05"), "Male");
        adminDAO.save(admin);

        Document document = new Document("dbfdfdz", "gooodssf manga", 10, "", true);
        documentDAO.saveWithAdminAuthorsPublisherGenre(document, admin,
                List.of("AK", "Boichi"), "Kd", List.of("action", "fantasy"));

        borrowDAO.borrowDocument(user, document);
        borrowDAO.returnDocument(userDAO.findByUsername("gokurrs234rt"), documentDAO.findByTitle("dbfdfdz"));

        assertEquals(1 , borrowDAO.findDocHasReturned(user).size());
        assertEquals(10, documentDAO.findByTitle("dbfdfdz").getQuantity());
    }
}
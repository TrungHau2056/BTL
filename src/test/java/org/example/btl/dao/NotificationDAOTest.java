package org.example.btl.dao;

import org.example.btl.model.HibernateUtils;
import org.example.btl.model.Notification;
import org.example.btl.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationDAOTest {
    private static SessionFactory sessionFactory;

    private NotificationDAO notificationDAO = new NotificationDAO();
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
    void saveAndDeleteTest() {
        User user = new User("name", "@mjfd", "goku234rt",
                "0123rethfg", Date.valueOf("2005-05-05"), "Male");
        userDAO.save(user);

        user = notificationDAO.saveWithUser(new Notification("test", "testMess"), user);

        assertEquals(1, user.getNotifications().size());

        Notification notification = null;
        for (Notification notification1 : user.getNotifications()) {
            notification = notification1;
        }

        user = notificationDAO.deleteNotification(notification);

        assertEquals(0, user.getNotifications().size());
    }

    @Test
    void deleteAllNotiTest() {
        User user = new User("namfde", "@mjfd", "goku2dd34rt",
                "0123rethfg", Date.valueOf("2005-05-05"), "Male");
        userDAO.save(user);

        user = notificationDAO.saveWithUser(new Notification("test", "testMess"), user);
        user = notificationDAO.saveWithUser(new Notification("test1", "testMess1"), user);
        user = notificationDAO.saveWithUser(new Notification("te2st", "tes2tMess"), user);

        user = notificationDAO.deleteAllNoti(user);

        assertEquals(0, user.getNotifications().size());
        assertEquals(0, notificationDAO.findAll().size());
    }
}
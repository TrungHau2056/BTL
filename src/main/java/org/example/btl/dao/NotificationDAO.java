package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.HibernateUtils;
import org.example.btl.model.Notification;
import org.example.btl.model.User;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotificationDAO {
    private Session session;

    public List<Notification> findAll() {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Notification", Notification.class);
        List<Notification> notifications = query.getResultList();

        session.close();
        return notifications;
    }

    public User saveWithUser(Notification notification, User user) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        user = session.merge(user);
        user.addNotification(notification);

        session.persist(notification);
        session.getTransaction().commit();
        session.close();

        return notification.getUser();
    }

    public User deleteNotification(Notification notification) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        notification = session.merge(notification);

        User user = notification.getUser();
        user.getNotifications().remove(notification);
        session.remove(notification);

        session.getTransaction().commit();
        session.close();

        return notification.getUser();
    }

    public User switchNotificationStatus(Notification notification) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        notification.setRead(!notification.isRead());
        notification = session.merge(notification);

        session.getTransaction().commit();
        session.close();

        return notification.getUser();
    }

    public User deleteAllNoti(User user) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        user = session.merge(user);
        for (Notification notification : user.getNotifications()) {
            session.remove(notification);
        }
        user.setNotifications(new HashSet<>());

        user = session.merge(user);
        session.getTransaction().commit();
        session.close();

        return user;
    }
}

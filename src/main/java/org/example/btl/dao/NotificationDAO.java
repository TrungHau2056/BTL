package org.example.btl.dao;

import org.example.btl.model.HibernateUtils;
import org.example.btl.model.Notification;
import org.example.btl.model.User;
import org.hibernate.Session;

public class NotificationDAO {
    private Session session;
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

        notification = session.get(Notification.class, notification.getId());

        User user = notification.getUser();
        user.getNotifications().remove(notification);
        session.remove(notification);

        session.getTransaction().commit();
        session.close();
        return user;
    }
}

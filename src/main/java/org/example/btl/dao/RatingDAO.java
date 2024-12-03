package org.example.btl.dao;

import jakarta.persistence.Query;
import org.example.btl.model.*;
import org.hibernate.Session;

import java.util.List;

public class RatingDAO {
    private Session session;

    /**
     * return the rating of a user on a doc.
     *
     * @param user
     * @param document
     * @return null if user hasn't rated this doc yet.
     */
    public Rating getUserRatingOnDoc(User user, Document document) {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Rating WHERE document =: document AND user =: user");
        query.setParameter("user", user);
        query.setParameter("document", document);
        List<Rating> ratings = query.getResultList();

        session.close();
        if (ratings.isEmpty()) {
            return null;
        }
        return ratings.getFirst();
    }

    /**
     * update if have already rated, add if haven't rated.
     *
     * @param user
     * @param document
     * @param score
     * @return the updated user.
     */
    public User updateOrAddRating(User user, Document document, int score) {
        Rating rating = getUserRatingOnDoc(user, document);

        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        if (rating == null) {
            rating = new Rating(score);
            user = session.merge(user);
            user.addRating(rating);

            document = session.merge(document);
            document.addRating(rating);

            session.persist(rating);
        } else {
            rating.setScore(score);
            session.merge(rating);
        }

        session.getTransaction().commit();
        session.close();

        return rating.getUser();
    }
}

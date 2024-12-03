package org.example.btl.service;

import org.example.btl.dao.RatingDAO;
import org.example.btl.model.Document;
import org.example.btl.model.Rating;
import org.example.btl.model.User;

public class RatingService {
    RatingDAO ratingDAO = new RatingDAO();

    public Rating getUserRatingOnDoc(User user, Document document) {
        return ratingDAO.getUserRatingOnDoc(user, document);
    }

    public User updateOrAddRating(User user, Document document, int score) {
        return ratingDAO.updateOrAddRating(user, document, score);
    }
}

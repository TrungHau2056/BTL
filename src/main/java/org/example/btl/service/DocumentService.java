package org.example.btl.service;

import org.example.btl.dao.AuthorDAO;
import org.example.btl.dao.DocumentDAO;
import org.example.btl.dao.GenreDAO;
import org.example.btl.dao.PublisherDAO;
import org.example.btl.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DocumentService {
    private DocumentDAO documentDAO = new DocumentDAO();
    private AuthorDAO authorDAO = new AuthorDAO();
    private GenreDAO genreDAO = new GenreDAO();
    private PublisherDAO publisherDAO = new PublisherDAO();

    public Document findByTitle(String title) {
        return documentDAO.findByTitle(title);
    }

    public List<Document> searchByTitleKeyword(String keyword) {
        return documentDAO.searchByTitleKeyword(keyword);
    }

    public List<Document> searchByAuthorKeyword(String keyword) {
        List<Author> authors = authorDAO.findByKeyword(keyword);
        List<Document> documentList = new ArrayList<>();
        for (Author author : authors) {
            for (Document document : author.getDocuments()) {
                documentList.add(document);
            }
        }
        return documentList;
    }

    public List<Document> searchByGenreKeyword(String keyword) {
        List<Genre> genres = genreDAO.findByKeyword(keyword);
        List<Document> documentList = new ArrayList<>();
        for (Genre genre : genres) {
            for (Document document : genre.getDocuments()) {
                documentList.add(document);
            }
        }
        return documentList;
    }

    public List<Document> searchByPublisherKeyword(String keyword) {
        List<Publisher> publishers = publisherDAO.findByKeyword(keyword);
        List<Document> documentList = new ArrayList<>();
        for (Publisher publisher : publishers) {
            for (Document document : publisher.getDocuments()) {
                documentList.add(document);
            }
        }
        return documentList;
    }

    public String validateSearchByKeyword(String keyword) {
        if (Objects.equals(keyword, "")) {
            return "Please enter your search keyword";
        }
        return null;
    }

    public boolean checkIfExist(List<String> authorNames, String title) {
        Document document = findByTitle(title);
        if (document == null) {
            return false;
        }
        Set<String> docAuthorNames = document.getAuthors().stream()
                .map(Author::getName)
                .collect(Collectors.toSet());
        return docAuthorNames.containsAll(authorNames);
    }

    public void saveWithAdminAuthorsPublisherGenre(Document document, Admin admin,
                                                   List<String> authorNames, String publisherName,
                                                   List<String> genreNames) {
        documentDAO.saveWithAdminAuthorsPublisherGenre(document, admin, authorNames, publisherName, genreNames);
    }

    public String validateAdd(String title, String author, String publisher, String quantityStr, String description) {
        if (Objects.equals(title, "")
                || Objects.equals(author, "")
                || Objects.equals(publisher, "")
                || Objects.equals(quantityStr, "")
                || Objects.equals(description, "")) {
            return "Please enter all the information!";
        }
        try {
            int quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            return "Quantity field must be a number!";
        }
        if (checkIfExist(new ArrayList<>(), title)) {
            return "This document has already been added";
        }
        return null;
    }

    public String validateAdd(String title, List<String> authorNames, List<String> genreNames, String publisher, String quantityStr, String description) {
        if (Objects.equals(title, "")
                || Objects.equals(publisher, "")
                || Objects.equals(quantityStr, "")
                || Objects.equals(description, "")) {
            return "Please enter all the information!";
        }

        if (authorNames.isEmpty() || genreNames.isEmpty()) {
            return "Please enter all the information!";
        }

        for (String authorName : authorNames) {
            if (Objects.equals(authorName, "")) {
                return "Please enter all the information!";
            }
        }

        for (String genreName : genreNames) {
            if (Objects.equals(genreName, "")) {
                return "Please enter all the information!";
            }
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            return "Quantity field must be a number!";
        }
        if (checkIfExist(new ArrayList<>(), title)) {
            return "This document has already been added";
        }
        return null;
    }
}

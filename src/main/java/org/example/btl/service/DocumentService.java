package org.example.btl.service;

import org.example.btl.dao.*;
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
    private BorrowDAO borrowDAO = new BorrowDAO();

    public List<Document> findAll() {
        return documentDAO.findAll();
    }

    public Document findByTitle(String title) {
        return documentDAO.findByTitle(title);
    }

    public List<Document> findDocCurrentBorrow(User user) {
        return borrowDAO.findDocCurrentBorrow(user);
    }

    public List<Document> findDocHasReturned(User user) {
        return null;
    }

    public List<Document> searchByTitle(String keyword, User user, String status) {
        switch (status) {
            case "All":
                return documentDAO.searchByTitleKeyword(keyword);
            case "Borrowed":
                return documentDAO.searchByTitleBorrowed(user, keyword);
            case "Not Borrowed":
                return documentDAO.searchByTitleNotBorrowed(user, keyword);
            default:
                throw new IllegalArgumentException();
        }
    }

    public List<Document> searchByAuthor(String keyword, User user, String status) {
        List<Author> authors= authorDAO.findByKeyword(keyword);;

        List<Document> documentList = new ArrayList<>();
        for (Author author : authors) {
            for (Document document : author.getDocuments()) {
                Borrow borrow = borrowDAO.findByUserCurrentlyBorrowsDocument(user, document);
                switch (status) {
                    case "All":
                        documentList.add(document);
                        break;
                    case "Borrowed":
                        if (borrow != null) {
                            documentList.add(document);
                        }
                        break;
                    case "Not Borrowed":
                        if (borrow == null) {
                            documentList.add(document);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
        return documentList;
    }

    public List<Document> searchByGenre(String keyword, User user, String status) {
        List<Genre> genres = genreDAO.findByKeyword(keyword);
        List<Document> documentList = new ArrayList<>();
        for (Genre genre : genres) {
            for (Document document : genre.getDocuments()) {
                Borrow borrow = borrowDAO.findByUserCurrentlyBorrowsDocument(user, document);
                switch (status) {
                    case "All":
                        documentList.add(document);
                        break;
                    case "Borrowed":
                        if (borrow != null) {
                            documentList.add(document);
                        }
                        break;
                    case "Not Borrowed":
                        if (borrow == null) {
                            documentList.add(document);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
        return documentList;
    }

    public List<Document> searchByPublisher(String keyword, User user, String status) {
        List<Publisher> publishers = publisherDAO.findByKeyword(keyword);
        List<Document> documentList = new ArrayList<>();
        for (Publisher publisher : publishers) {
            for (Document document : publisher.getDocuments()) {
                Borrow borrow = borrowDAO.findByUserCurrentlyBorrowsDocument(user, document);
                switch (status) {
                    case "All":
                        documentList.add(document);
                        break;
                    case "Borrowed":
                        if (borrow != null) {
                            documentList.add(document);
                        }
                        break;
                    case "Not Borrowed":
                        if (borrow == null) {
                            documentList.add(document);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
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

    public boolean checkIfExist(List<String> authorNames, String title, String description) {
        Document document = findByTitle(title);
        if (document == null) {
            return false;
        }
        if (!Objects.equals(description, document.getDescription())) {
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

    public String validateAdd(String title, List<String> authorNames, List<String> genreNames, String quantityStr, String description) {
        if (Objects.equals(title, "")
                || Objects.equals(quantityStr, "")) {
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
        if (checkIfExist(authorNames, title, description)) {
            return "This document has already been added";
        }
        return null;
    }
}

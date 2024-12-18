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

    public void update(Document document) {
        documentDAO.update(document);
    }

    public void deleteDocument(Document document) {
        documentDAO.deleteDocument(document);
    }

    public List<Document> findAll() {
        return documentDAO.findAll();
    }

    public Document findByTitle(String title) {
        return documentDAO.findByTitle(title);
    }

    public List<Document> findDocCurrentlyBorrow(User user) {
        return borrowDAO.findDocCurrentlyBorrow(user);
    }

    public List<User> findUserCurrentlyBorrow(Document document) {
        return borrowDAO.findUserCurrentlyBorrow(document);
    }

    /**
     * search Document by title keyword, borrower and status.
     *
     * @param keyword The keyword to search for in the title of the documents.
     * @param user The user performing the search, used to filter documents based on borrowing status.
     * @param status The status of the documents to be searched. It can be:
     *               - "All": Searches for all documents matching the keyword.
     *               - "Borrowed": Searches for documents that are borrowed by the user.
     *               - "Not Borrowed": Searches for documents that are not borrowed by the user.
     * @return A list of Document that match the search criteria.
     * @throws IllegalArgumentException If an invalid status is provided.
     */
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

    /**
     * search Document by author keyword, borrower and status.
     *
     * @param keyword The keyword to search for in the author's name.
     * @param user The user performing the search, used to filter documents based on borrowing status.
     * @param status The status of the documents to be searched. It can be:
     *               - "All": Searches for all documents by authors matching the keyword.
     *               - "Borrowed": Searches for documents borrowed by the user.
     *               - "Not Borrowed": Searches for documents not borrowed by the user.
     * @return A list of Document that match the search criteria.
     * @throws IllegalArgumentException If an invalid status is provided.
     */
    public List<Document> searchByAuthor(String keyword, User user, String status) {
        List<Author> authors= authorDAO.findByKeyword(keyword);;

        List<Document> documentList = new ArrayList<>();
        for (Author author : authors) {
            for (Document document : authorDAO.getDocuments(author)) {
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

    /**
     * search Document by genre keyword, borrower and status.
     *
     * @param keyword The keyword to search for in the genre's name.
     * @param user The user performing the search, used to filter documents based on borrowing status.
     * @param status The status of the documents to be searched. It can be:
     *               - "All": Searches for all documents by genres matching the keyword.
     *               - "Borrowed": Searches for documents borrowed by the user.
     *               - "Not Borrowed": Searches for documents not borrowed by the user.
     * @return A list of Document that match the search criteria.
     * @throws IllegalArgumentException If an invalid status is provided.
     */
    public List<Document> searchByGenre(String keyword, User user, String status) {
        List<Genre> genres = genreDAO.findByKeyword(keyword);

        List<Document> documentList = new ArrayList<>();
        for (Genre genre : genres) {
            for (Document document : genreDAO.getDocuments(genre)) {
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

    /**
     * search Document by publisher keyword, borrower and status.
     *
     * @param keyword The keyword to search for in the publisher's name.
     * @param user The user performing the search, used to filter documents based on borrowing status.
     * @param status The status of the documents to be searched. It can be:
     *               - "All": Searches for all documents by publishers matching the keyword.
     *               - "Borrowed": Searches for documents borrowed by the user.
     *               - "Not Borrowed": Searches for documents not borrowed by the user.
     * @return A list of Document that match the search criteria.
     * @throws IllegalArgumentException If an invalid status is provided.
     */
    public List<Document> searchByPublisher(String keyword, User user, String status) {
        List<Publisher> publishers = publisherDAO.findByKeyword(keyword);
        List<Document> documentList = new ArrayList<>();
        for (Publisher publisher : publishers) {
            for (Document document : publisherDAO.getDocuments(publisher)) {
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

    /**
     * validate search.
     *
     * @param keyword The search keyword entered by the user.
     * @return null if the keyword is valid.
     */
    public String validateSearchByKeyword(String keyword) {
        if (Objects.equals(keyword, "")) {
            return "Please enter your search keyword";
        }
        return null;
    }

    /**
     * check if a document is already in the database.
     *
     * @param authorNames The list of author names to be checked against the document's authors.
     * @param title The title of the document to search for.
     * @param description The description of the document to compare with the found document's description.
     * @return true, false.
     */
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

    public Document updateDocument(Document document,
                               List<String> authorNames, String publisherName,
                               List<String> genreNames) {
        return documentDAO.updateDocument(document, authorNames, publisherName, genreNames);
    }

    /**
     * check if a doc can be added to the database.
     *
     * @param title
     * @param authorNames
     * @param genreNames
     * @param quantityStr
     * @param description
     * @return null if a doc can be added.
     */
    public String validateAddDoc(String title, List<String> authorNames, List<String> genreNames, String quantityStr, String description) {
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
            Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            return "Quantity field must be a number!";
        }
        if (checkIfExist(authorNames, title, description)) {
            return "This document has already been added";
        }
        return null;
    }

    /**
     * check if a doc can be updated.
     *
     * @param title The title of the document.
     * @param authorNames A list of author names associated with the document.
     * @param genreNames A list of genre names associated with the document.
     * @param quantityStr The quantity of the document.
     * @param description The description of the document.
     * @return null if a doc can be updated.
     */
    public String validateUpdateDoc(String title, List<String> authorNames, List<String> genreNames, String quantityStr, String description) {
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
            Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            return "Quantity field must be a number!";
        }

        return null;
    }

    public boolean isCurrentlyBorrow(Document document) {
        return documentDAO.isCurrentlyBorrow(document);
    }

    public List<Document> findDocAddedByAdmin(Admin admin) {
        return documentDAO.findDocAddedByAdmin(admin);
    }

    public List<Rating> getRatings(Document document) {
        return documentDAO.getRating(document);
    }
}

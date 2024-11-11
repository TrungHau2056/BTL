package org.example.btl.service;

import org.example.btl.dao.DocumentDAO;
import org.example.btl.model.Author;
import org.example.btl.model.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DocumentService {
    private final DocumentDAO documentDAO = new DocumentDAO();

    public Document findByTitle(String title) {
        return documentDAO.findByTitle(title);
    }

    public List<Document> searchByTitleKeyword(String keyword) {
        return documentDAO.searchByTitleKeyword(keyword);
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
}

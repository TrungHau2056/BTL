package org.example.btl.service;

import org.example.btl.dao.DocumentDAO;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {
    @Mock
    DocumentDAO documentDAO;

    @InjectMocks
    DocumentService documentService;

    private List<String> authorNames = new ArrayList<>();
    private List<String> genreNames = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void checkIfExistTrue() {
        String title = "Test manga";
        List<String> authorNames = new ArrayList<>();
        authorNames.add("Akira Toriyama");
        authorNames.add( "Inoue Takehiko");

        Set<Author> authors = new HashSet<>();
        authors.add(new Author("Inoue Takehiko"));
        authors.add(new Author("Akira Toriyama"));

        Document document = mock(Document.class);
        when(document.getAuthors()).thenReturn(authors);
        when(document.getDescription()).thenReturn("Test description");
        when(documentDAO.findByTitle(title)).thenReturn(document);

        assertTrue(documentService.checkIfExist(authorNames, title, "Test description"));
        verify(documentDAO, times(1)).findByTitle(title);
    }

    @Test
    void checkIfExistFalse() {
        String title = "Test manga";
        List<String> authorNames = new ArrayList<>();
        authorNames.add("Akira Toriyama");
        authorNames.add( "Inoue Takehiko");

        Set<Author> authors = new HashSet<>();
        authors.add(new Author("Inoue Takehiko"));
        authors.add(new Author("CLAMP"));
        authors.add(new Author("Yasuhisa Hara"));

        Document document = mock(Document.class);
        when(document.getAuthors()).thenReturn(authors);
        when(documentDAO.findByTitle(title)).thenReturn(document);

        assertFalse(documentService.checkIfExist(authorNames, title, "Test description"));
        verify(documentDAO, times(1)).findByTitle(title);
    }

    @Test
    void validateAdd_quantity() {
        authorNames.add("Boichi");
        genreNames.add("Action");
        genreNames.add("Fantasy");
        String result = documentService.validateAddDoc("title", authorNames, genreNames, "one-hundred", "");
        assertEquals("Quantity field must be a number!", result);
    }

    @Test
    void validateAdd_emptyField() {
        authorNames.add("Boichi");
        String result = documentService.validateAddDoc("title", authorNames, genreNames, "", "");
        assertEquals("Please enter all the information!", result);
    }

    @Test
    void validateUpdate_success() {
        assertNull(documentService.validateUpdateDoc("titlee", List.of("haha"),
                List.of("Action", "Comedy"), "15", "Hay"));
    }
}
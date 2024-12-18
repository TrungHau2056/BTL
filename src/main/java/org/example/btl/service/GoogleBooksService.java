package org.example.btl.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.v1.Books;
import com.google.api.services.books.v1.BooksRequestInitializer;
import com.google.api.services.books.v1.model.Volume;
import com.google.api.services.books.v1.model.Volumes;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleBooksService {
    private static GoogleBooksService instance;
    private final Books books;
    static final String API_KEY = "AIzaSyCw-gxpbGAv0MbhDPJ-qbCCV6l69NtULqY";
    private static final String APPLICATION_NAME = "Library";
    private JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    /**
     * constructor.
     *
     * @throws GeneralSecurityException If there is a security issue when setting up the HTTP transport.
     * @throws IOException If there is an error in reading the JSON factory or initializing the API client.
     */
    private GoogleBooksService() throws GeneralSecurityException, IOException {
        books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * get instance.
     *
     * @return a GoogleBooksService instance.
     * @throws GeneralSecurityException if a security error occurs.
     * @throws IOException if an I/O error occurs.
     */
    public static GoogleBooksService getInstance() throws GeneralSecurityException, IOException {
        if (instance == null) {
            instance = new GoogleBooksService();
        }
        return instance;
    }

    /**
     * perform a search by ISBN.
     *
     * @param isbn the ISBN of the book to search for
     * @return the VolumeInfo of the first book matching the ISBN, or null if no match is found
     * @throws IOException if an I/O error occurs during the search
     */
    public Volume.VolumeInfo searchByISBN(String isbn) throws IOException {
        Books.Volumes.List volumesList = books.volumes().list("isbn:" + isbn);
        Volumes volumes = volumesList.execute();
        System.out.println(volumes.getTotalItems());
        if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
            return null;
        }

        return volumes.getItems().get(0).getVolumeInfo();
    }
}

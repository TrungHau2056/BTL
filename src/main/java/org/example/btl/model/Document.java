package org.example.btl.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.engine.spi.CascadeStyle;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private String imageLink;
    private int quantity;
    private boolean isAddedByISBN;

    @Temporal(TemporalType.DATE)
    private Date addedDate;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "document-author",
            joinColumns = {@JoinColumn(name = "document_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "document-genre",
            joinColumns = {@JoinColumn(name = "document_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private Set<Genre> genres  = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "document", cascade = CascadeType.MERGE)
    private Set<Borrow> borrows = new HashSet<>();

    @OneToMany(mappedBy = "document")
    private Set<Rating> ratings = new HashSet<>();

    public Document() {
    }

    public Document(String title, String description, int quantity, String imageLink, boolean isAddedByISBN) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.imageLink = imageLink;
        this.isAddedByISBN = isAddedByISBN;
        addedDate = Date.valueOf(LocalDate.now());
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAddedByISBN() {
        return isAddedByISBN;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void decreaseQuantity() {
        --quantity;
    }

    public void increaseQuantity() {
        ++quantity;
    }

    public Set<Borrow> getBorrows() {
        return borrows;
    }

    public void addBorrow(Borrow borrow) {
        borrows.add(borrow);
        borrow.setDocument(this);
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setDocument(this);
    }
}

package com.betoniarka.biblioteka.appuser;

import com.betoniarka.biblioteka.book.Book;
import com.betoniarka.biblioteka.borrow.Borrow;
import com.betoniarka.biblioteka.exceptions.ResourceConflictException;
import com.betoniarka.biblioteka.queueentry.QueueEntry;
import com.betoniarka.biblioteka.review.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "app_user")
public class AppUser {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column(unique = true)
    @NotNull(message = "username is required")
    private String username;

    @Getter
    @Setter
    @Column
    private String firstname;

    @Getter
    @Setter
    @Column
    private String lastname;

    @Getter
    @Setter
    @Column(unique = true)
    @NotNull(message = "email is required")
    private String email;

    @Getter
    @Setter
    @Column
    @NotNull(message = "password is required")
    private String password;

    @Getter
    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private AppUserRole role;

    @Getter
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Borrow> borrows = new ArrayList<>();

    @Getter
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<QueueEntry> queuedBooks = new ArrayList<>();

    @Getter
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public AppUser() {}

    // TODO add queue checks
    public void borrowBook(Borrow borrow, Book book) {
        if (getCurrentBorrows().size() >= 3)
            throw new ResourceConflictException("User '%s' already has 3 books borrowed".formatted(this.username));

        if (getCurrentBorrows().stream().anyMatch(b -> b.getBook().equals(book)))
            throw new ResourceConflictException("User '%s' already borrowed book '%s'".formatted(this.username, book.getTitle()));


        book.decrementCount();

        borrow.setAppUser(this);
        borrow.setBook(book);
        borrow.setBorrowedAt(Instant.now());

        this.borrows.add(borrow);
        book.getBorrowedBy().add(borrow);
    }

    public void returnBook(Borrow borrow) {
        if (borrow.getAppUser() != this)
            throw new IllegalStateException("Borrow '%d' belongs to another user".formatted(borrow.getId()));
        if (borrow.isReturned())
            throw new ResourceConflictException("Borrow '%d' is already returned".formatted(borrow.getId()));

        borrow.getBook().incrementCount();
        borrow.setReturnedAt(Instant.now());
    }

    public List<Borrow> getCurrentBorrows() {
        return this.borrows.stream().filter(b -> !b.isReturned()).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        AppUser appUser = (AppUser) o;
        return id == appUser.id &&
                Objects.equals(email, appUser.email) &&
                Objects.equals(firstname, appUser.firstname) &&
                Objects.equals(lastname, appUser.lastname) &&
                Objects.equals(role, appUser.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstname, lastname, role);
    }

}

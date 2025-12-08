package com.betoniarka.biblioteka.appuser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int appUserId;

    @Column(length = 100, unique = true)
    @Email
    private String email;

    @Column
    @NotBlank
    private String firstname;

    @Column
    @NotBlank
    private String lastname;

//    @ManyToMany
//    @JoinTable(
//            name = "student_course",
//            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "ID"),
//            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "ID"))
//    private Set<Student> studentSet = new HashSet<>();
//
//    @OneToMany(mappedBy = "course")
//    private Set<Grade> gradeSet = new HashSet<>();

    public AppUser() {
    }

    public int getAppUserId() {
        return appUserId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return appUserId == appUser.appUserId &&
                Objects.equals(email, appUser.email) &&
                Objects.equals(firstname, appUser.firstname) &&
                Objects.equals(lastname, appUser.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, email, firstname, lastname);
    }

}

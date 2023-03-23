package ro.mycode.librarieapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "student")
@Entity(name = "Student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Student implements Comparable<Student> {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "student_sequence")
    Long id;

    @Column(name = "first_name",
            nullable = false)
    String firstName;

    @Column(name = "second_name",
            nullable = false)
    String secondName;

    @Column(name = "email",
            nullable = false)
    @Email
    String email;

    @DecimalMax(value = "25", message = "Cursul este alocat persoanelor sub 25 de ani")
    @Column(name = "age",
            nullable = false)
    double age;


    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL

    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    @Builder.Default
    List<Book> books = new ArrayList<>();


    public void addBook(Book book) {
        this.books.add(book);
        book.setStudent(this);
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }


    public boolean vfExistsBook(Book book) {
        return this.books.contains(book);
    }


    @Override
    public int compareTo(Student o) {
        return 0;
    }
}

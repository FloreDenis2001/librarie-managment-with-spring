package ro.mycode.librarieapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.hibernate.annotations.LazyCollection;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_sequence"
    )

    @Column(
            name = "id"
    )
    private Long id;


    @Column(
            name = "student_username",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotEmpty
    @Size(min=4,max=10,message ="Numarul minim de caractere este 4")

    private String userName;
    @Column(
            name = "student_password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotEmpty
    @Size(min=8,message = "Numarul minim de caractere este 8")
    private String password;

    @Column(
            name = "student_email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotEmpty
    @Size (min=8,message ="Numarul minim de carcatere este 8")
    private String email;

    @Column(
            name = "student_varsta",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    @NotEmpty
    @Min(value = 18,message = "Studentul trebuie sa aibe varsta minima de 18 ani")
    private int varsta;

    @Column(
            name = "student_phone",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotEmpty
    @Size (min=10,message ="Numarul minim de carcatere este 10")
    private String phone;


    @OneToMany(mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Carte> bookList = new ArrayList<>();

    @Override
    public String toString() {
        String text = " Id : " + this.id;
        text += "UserName : " + this.userName;
        text += "Password : " + this.password;
        text += "Email : " + this.email;
        text += "Phone : " + this.phone;

        return text;
    }

    @Override
    public boolean equals(Object o) {
        Student s = (Student) o;
        return s.password.compareTo(this.getPassword()) == 0;
    }


    @Override
    public int compareTo(Student o) {
        if (o.getVarsta() > this.getVarsta()) {
            return 1;
        } else if (o.getVarsta() < this.getVarsta()) {
            return -1;
        } else {
            return 0;
        }
    }

    public void addBook(Carte book){
        this.bookList.add(book);
        book.setStudent(this);
    }

    public void removeBook(Carte book){
        this.bookList.remove(book.getId());
    }


    public boolean vfExistsBook(Carte carte){
        return  this.bookList.contains(carte);
    }





}

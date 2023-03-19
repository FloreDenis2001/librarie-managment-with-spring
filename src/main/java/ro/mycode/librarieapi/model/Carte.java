package ro.mycode.librarieapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;


@Table(name = "carte")
@Entity(name = "Carte")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Carte implements Comparable<Carte> {

    @Id
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)

    @GeneratedValue(strategy = SEQUENCE, generator = "book_sequence")

    @Column(name = "id")

    private Long id;


    @Column(name = "book_name", nullable = false, columnDefinition = "TEXT")
    @NotEmpty
    @Size (min=3,message="The book has to have minim 3 characters")
    private String bookName;

    @Column(name = "create_at", nullable = false, columnDefinition = "DATE")
    private LocalDate createdAt;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    @NotEmpty
    @Size(min = 2,message = "The description has to have minim 2 characters")
    private String description;


    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "student_id_fk"))
    @JsonBackReference
    private Student student;


    @Override
    public String toString() {
        String text = "Id : " + this.id;
        text += "Book Name : " + this.bookName;
        text += "Description : " + this.description;
        text += "Student UserName: " + this.student.getUserName();
        text += "Create At : " + this.createdAt;

        return text;
    }

    @Override
    public boolean equals(Object o) {
        Carte x = (Carte) o;
        return x.bookName.equals(this.bookName);
    }

    @Override
    public int compareTo(Carte c) {

        if (c.getCreatedAt().getYear() > this.getCreatedAt().getYear()) {
            return 1;
        } else if (c.getCreatedAt().getYear() < this.getCreatedAt().getYear()) {
            return -1;
        } else {
            return 0;
        }
    }

}

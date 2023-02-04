package ro.mycode.librarieapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "carte")
@Entity(name = "Carte")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carte implements Comparable<Carte> {

    @Id
    @SequenceGenerator(name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "book_sequence"
    )

    @Column(name = "id")

    private Long id;


    @Column(name = "book_name",
            nullable = false, columnDefinition = "TEXT")

    @NotEmpty
    private String book_name;

    @Column(
            name = "create_at",
            nullable = false,
            columnDefinition = "DATE"
    )

    private LocalDate created_at;

    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotEmpty
    private String description;


    @ManyToOne
    @JoinColumn(name = "id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_id_fk")
    )
    private Student student;


    @Override
    public String toString() {
        String text = "Id : " + this.id;
        text += "Book Name : " + this.book_name;
        text += "Description : " + this.description;
        text += "Student UserName: " + this.student.getUserName();
        text += "Create At : " + this.created_at;

        return text;
    }

    @Override
    public boolean equals(Object o) {
        Carte x = (Carte) o;
        return x.getStudent() == this.student;
    }

    @Override
    public int compareTo(Carte c) {

        if (c.getCreated_at().getYear() > this.getCreated_at().getYear()) {
            return 1;
        } else if (c.getCreated_at().getYear() < this.getCreated_at().getYear()) {
            return -1;
        } else {
            return 0;
        }
    }

}

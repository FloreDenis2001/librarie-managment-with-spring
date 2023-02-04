package ro.mycode.librarieapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name="student")
@Entity(name="Student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Comparable<Student>{
    @Id
    @SequenceGenerator(
            name="student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "books_sequence"
    )

    @Column(
            name="id"
    )
    private Long id;


    @Column(
            name="student_username",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotEmpty
    private String userName;
    @Column(
            name="student_password",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotEmpty
    private String password;

    @Column(
            name="student_email",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotEmpty
    private String email;

    @Column(
            name="student_email",
            nullable = false,
            columnDefinition = "INTEGER"
    )
    @NotEmpty
    private int varsta;

    @Column(
            name="student_phone",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @NotEmpty
    private String phone;


    @OneToMany(mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Carte> bookList = new ArrayList<>();

    @Override
    public String toString(){
        String text=" Id : "+this.id;
        text+="UserName : "+this.userName;
        text+="Password : "+this.password;
        text+="Email : "+this.email;
        text+="Phone : "+this.phone;

        return text;
    }

    @Override
    public boolean equals(Object o){
        Student s= (Student) o;
        return s.password.compareTo(this.getPassword())==0;
    }

    @Override
    public int compareTo(Student o) {
        if(o.getVarsta()>this.getVarsta()){
            return 1;
        }else if(o.getVarsta()<this.getVarsta()){
            return -1;
        }else {
            return 0;
        }
    }










}

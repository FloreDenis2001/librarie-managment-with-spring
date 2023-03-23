package ro.mycode.librarieapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.mycode.librarieapi.LibrarieApiApplication;
import ro.mycode.librarieapi.model.Student;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LibrarieApiApplication.class)
class StudentRepoTest {
    @Autowired
    StudentRepo studentRepo;


    @BeforeEach
    public void test() {
        studentRepo.deleteAll();
    }

    @Test
    void findStudentsByEmail() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            students.add(new Student().builder().age(18 + i).email("denis" + i + "@yahoo.com").firstName("Flore" + i).secondName("Denis" + i).build());
        }

        studentRepo.saveAll(students);
        assertEquals(18, studentRepo.findStudentsByEmail("denis0@yahoo.com").get().getAge());

    }


}
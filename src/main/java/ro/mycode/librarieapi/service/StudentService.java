package ro.mycode.librarieapi.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import ro.mycode.librarieapi.dto.CreateBookRequest;
import ro.mycode.librarieapi.exceptii.CarteDoesNotExist;
import ro.mycode.librarieapi.exceptii.EmptyDataBase;
import ro.mycode.librarieapi.exceptii.StudentAlreadyExists;
import ro.mycode.librarieapi.exceptii.StudentDoesNotExist;
import ro.mycode.librarieapi.model.Book;
import ro.mycode.librarieapi.model.Student;
import ro.mycode.librarieapi.repository.BookRepo;
import ro.mycode.librarieapi.repository.StudentRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class StudentService {

    private StudentRepo studentRepo;

    private BookRepo bookRepository;


    public StudentService(StudentRepo studentRepo, BookRepo bookRepository) {
        this.studentRepo = studentRepo;
        this.bookRepository = bookRepository;
    }

    public List<Student> getAllStudents() {
        List<Student> students = studentRepo.findAll();
        if (students.size() == 0) {
            throw new EmptyDataBase("DataBase Empty!");
        }
        return students;
    }

    @Transactional
    @Modifying
    public void addStudent(Student s) {
        Optional<Student> student = studentRepo.findStudentsByEmail(s.getEmail());
        if (student.isEmpty()) {
            studentRepo.save(s);
        } else {
            throw new StudentAlreadyExists("Studentul exista deja!");
        }
    }

    @Transactional
    @Modifying
    public void removeStudent(String email) {
        Optional<Student> student = studentRepo.findStudentsByEmail(email);
        if (!student.isEmpty()) {
            studentRepo.removeByEmail(email);
        } else {
            throw new StudentDoesNotExist("Studentul nu exista!");
        }
    }

    @Transactional
    @Modifying
    public void addBook(CreateBookRequest createBookRequestDTO) throws CarteDoesNotExist, StudentDoesNotExist {

        Optional<Student> student = studentRepo.findById(createBookRequestDTO.getIdStudent());


        if (student.isEmpty()) {
            throw new StudentDoesNotExist("Student doesn't exist! ");
        }


        Book book = Book.builder().
                title(createBookRequestDTO.getTitle()).
                author(createBookRequestDTO.getAuthor())
                .price(createBookRequestDTO.getPrice()).stars(createBookRequestDTO.getStars()).build();

        if (!bookRepository.getBookByStudentAndTitle(student.get().getId(), book.getTitle()).isEmpty()) {
            throw new CarteDoesNotExist("Book doesn't exist!");
        }

        student.get().addBook(book);
        studentRepo.saveAndFlush(student.get());
    }





    @Transactional
    @Modifying
    public void removeBook(CreateBookRequest createBookRequest) throws CarteDoesNotExist, StudentDoesNotExist {
        Optional<Student> student = studentRepo.findById(createBookRequest.getIdStudent());

        if (student.isEmpty())
            throw new StudentDoesNotExist("Studentul nu exista !");

        Optional<Book> book = bookRepository.getBookByStudentAndAuthorAndTitle(createBookRequest.getIdStudent(), createBookRequest.getAuthor(),createBookRequest.getTitle());
        if (book.isEmpty())
            throw new CarteDoesNotExist("Cartea nu exista !");
        bookRepository.removeBookByStudentAndTitle(createBookRequest.getIdStudent(), createBookRequest.getTitle());
        studentRepo.saveAndFlush(student.get());
    }


}

package ro.mycode.librarieapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.librarieapi.dto.CreateBookRequest;
import ro.mycode.librarieapi.exceptii.CarteDoesNotExist;
import ro.mycode.librarieapi.exceptii.EmptyDataBase;
import ro.mycode.librarieapi.exceptii.StudentAlreadyExists;
import ro.mycode.librarieapi.exceptii.StudentDoesNotExist;
import ro.mycode.librarieapi.model.Book;
import ro.mycode.librarieapi.model.Student;
import ro.mycode.librarieapi.repository.BookRepo;
import ro.mycode.librarieapi.repository.StudentRepo;
import ro.mycode.librarieapi.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepo studentRepo;

    @Mock
    private BookRepo bookRepository;


    @InjectMocks
    private StudentService studentService;

    @Captor
    ArgumentCaptor<String> studentFiledsArgumentCapture;

    @BeforeEach
    public void test() {
        studentRepo.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void getAllStudent() {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            students.add(new Student().builder().age(18 + i).email("denis" + i + "@yahoo.com").firstName("Flore" + i).secondName("Denis" + i).build());
        }
        doReturn(students).when(studentRepo).findAll();
        assertEquals(students, studentService.getAllStudents());
    }

    @Test
    public void getAllStudentException() {
        doReturn(new ArrayList<>()).when(studentRepo).findAll();
        assertThrows(EmptyDataBase.class, () -> {
            studentService.getAllStudents();
        });
    }


    @Test
    void addStudent() {
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        studentService.addStudent(s);
        doReturn(student).when(studentRepo).findStudentsByEmail(s.getEmail());

        assertEquals("Flore", studentRepo.findStudentsByEmail(s.getEmail()).get().getFirstName());
    }

    @Test
    void removeStudent() {
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findStudentsByEmail(s.getEmail());
        studentService.removeStudent(s.getEmail());
        verify(studentRepo, times(1)).removeByEmail(studentFiledsArgumentCapture.capture());
        assertEquals("denis@yahoo.com", studentFiledsArgumentCapture.getValue());
    }

    @Test
    void removeStudentException() {
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        doReturn(Optional.empty()).when(studentRepo).findStudentsByEmail(s.getEmail());
        assertThrows(StudentDoesNotExist.class, () -> {
            studentService.removeStudent(s.getEmail());
        });
    }

    @Test
    void addStudentException() {
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);

        doReturn(student).when(studentRepo).findStudentsByEmail(s.getEmail());
        assertThrows(StudentAlreadyExists.class, () -> {
            studentService.addStudent(s);
        });
    }

    @Test
    void addBook() {
        CreateBookRequest createBookRequest = new CreateBookRequest().builder().idStudent(1L).stars(5L).title("Harry Potter").price(20).author("Flore Denis").build();
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findById(createBookRequest.getIdStudent());
        studentService.addBook(createBookRequest);
        assertEquals("Harry Potter", studentRepo.findById(createBookRequest.getIdStudent()).get().getBooks().get(0).getTitle());
    }

    @Test
    void addBookException() {
        CreateBookRequest createBookRequest = new CreateBookRequest().builder().idStudent(1L).stars(5L).title("Harry Potter").price(20).author("Flore Denis").build();
        doReturn(Optional.empty()).when(studentRepo).findById(createBookRequest.getIdStudent());
        assertThrows(StudentDoesNotExist.class, () -> {
            studentService.addBook(createBookRequest);
        });
    }

    @Test
    void addBookException2() {
        CreateBookRequest createBookRequest = new CreateBookRequest().builder().idStudent(1L).stars(5L).title("Harry Potter").price(20).author("Flore Denis").build();
        Student s = new Student().builder().id(1L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findById(createBookRequest.getIdStudent());
        Book book = Book.builder().
                title(createBookRequest.getTitle()).
                author(createBookRequest.getAuthor())
                .price(createBookRequest.getPrice()).stars(createBookRequest.getStars()).build();
        Optional<Book> bookopt = Optional.of(book);
        doReturn(bookopt).when(bookRepository).getBookByStudentAndTitle(createBookRequest.getIdStudent(), createBookRequest.getTitle());
        assertThrows(CarteDoesNotExist.class, () -> {
            studentService.addBook(createBookRequest);
        });
    }


    @Test
    void removeBook() {
        Student s = new Student().builder().id(5L).age(21).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        CreateBookRequest createBookRequest = new CreateBookRequest().builder().idStudent(s.getId()).stars(5L).title("Harry Potter").price(20).author("Flore Denis").build();
        Optional<Student> student = Optional.of(s);
        doReturn(student).when(studentRepo).findById(createBookRequest.getIdStudent());
        Book book = Book.builder().
                title(createBookRequest.getTitle()).
                author(createBookRequest.getAuthor())
                .price(createBookRequest.getPrice()).stars(createBookRequest.getStars()).build();
        Optional<Book> bookopt = Optional.of(book);
        doReturn(bookopt).when(bookRepository).getBookByStudentAndAuthorAndTitle(createBookRequest.getIdStudent(),createBookRequest.getAuthor(), createBookRequest.getTitle());
        studentService.addBook(createBookRequest);
        doNothing().when(bookRepository).removeBookByStudentAndTitle(createBookRequest.getIdStudent(),createBookRequest.getTitle());
        studentService.removeBook(createBookRequest);
        assertEquals(new ArrayList<>(),student.get().getBooks());
    }

}
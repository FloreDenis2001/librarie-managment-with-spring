package ro.mycode.librarieapi.repository;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ro.mycode.librarieapi.LibrarieApiApplication;
import ro.mycode.librarieapi.model.Book;
import ro.mycode.librarieapi.model.Student;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LibrarieApiApplication.class)
class BookRepoTest {

    @Autowired
    BookRepo bookRepository;
    @Autowired
    StudentRepo studentRepo;


    @BeforeEach
    public void deleteDataBase() {
        studentRepo.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @Transactional
    void orderBooksAscendentByPrice() {

        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x = new Student().builder().id(1L).age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(faker.number().numberBetween(1L, 5L)).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(18, bookRepository.orderBooksAscendentByPrice().get().get(0).getPrice());

    }

    @Test
    void orderBooksDescendentByPrice() {
        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x = new Student().builder().id(1L).age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(faker.number().numberBetween(1L, 5L)).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(20, bookRepository.orderBooksDescendentByPrice().get().get(0).getPrice());
    }

    @Test
    void lowestPriceBook() {
        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x = new Student().builder().id(1L).age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(faker.number().numberBetween(1L, 5L)).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(2, bookRepository.lowestPriceBook(18).get().size());

        //todo mapper ca sa compar 2 stringuri!
    }

    @Test
    void highPriceBook() {
        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x = new Student().builder().id(1L).age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(faker.number().numberBetween(1L, 5L)).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(3, bookRepository.highPriceBook(21).get().size());
    }

    @Test
    void bestBooks() {

        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x = new Student().builder().id(1L).age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(5L).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(5L, bookRepository.bestBooks().get().get(0).getStars());

    }

    @Test
    void filterStarBook() {
        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x = new Student().builder().id(1L).age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(5L).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        assertEquals(3, bookRepository.filterStarBook(5L).get().size());
    }

    @Test
    void getBookByStudentAndTitle() {

        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x = new Student().builder().id(1L).age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(5L).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        studentRepo.saveAndFlush(find.get());
        assertEquals(booksAll.get(0), bookRepository.getBookByStudentAndTitle(find.get().getId(), booksAll.get(0).getTitle()).get());

    }

    @Test
    void removeBookByStudentAndTitle() {

        Faker faker = new Faker();
        List<Book> books = new ArrayList<>();
        Student x = new Student().builder().id(1L).age(18).email("denis@yahoo.com").firstName("Flore").secondName("Denis").build();
        studentRepo.saveAndFlush(x);


        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().author(faker.book().author()).price(20 - i).stars(5L).title(faker.book().title()).build());
        }
        bookRepository.saveAllAndFlush(books);


        Optional<Student> find = studentRepo.findStudentsByEmail("denis@yahoo.com");
        List<Book> booksAll = bookRepository.findAll();
        find.get().addBook(booksAll.get(0));
        find.get().addBook(booksAll.get(1));
        find.get().addBook(booksAll.get(2));
        studentRepo.saveAndFlush(find.get());
        bookRepository.removeBookByStudentAndTitle(find.get().getId(), booksAll.get(0).getTitle());
        assertEquals(Optional.empty(), bookRepository.getBookByStudentAndTitle(find.get().getId(), booksAll.get(0).getTitle()));

    }
}
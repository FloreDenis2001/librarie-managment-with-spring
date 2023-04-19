package ro.mycode.librarieapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.mycode.librarieapi.exceptii.EmptyDataBase;
import ro.mycode.librarieapi.model.Book;
import ro.mycode.librarieapi.repository.BookRepo;
import ro.mycode.librarieapi.repository.StudentRepo;
import ro.mycode.librarieapi.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepo bookRepository;

    @Mock
    private StudentRepo studentRepo;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void test() {
        bookRepository.deleteAll();
        studentRepo.deleteAll();
    }


    @Test
    void getAllBooks() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().stars(5L).title("Harry Potter").price(20).author("Flore Denis").build());
        }

        doReturn(books).when(bookRepository).findAll();

        assertEquals(3, bookService.getAllBooks().size());
    }

    @Test
    void getAllBooksException() {
        doReturn(new ArrayList<>()).when(bookRepository).findAll();

        assertThrows(EmptyDataBase.class, () -> {
            bookService.getAllBooks();
        });
    }

    @Test
    void getAllBooksGraterPriceThan() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            books.add(new Book().builder().stars(5L).title("Harry Potter").price(22).author("Flore Denis").build());
        }

        doReturn(Optional.of(books)).when(bookRepository).lowestPriceBook(20);
        assertEquals(3, bookService.getAllBooksGraterPriceThan(20).get().size());
    }


    @Test
    void getAllBooksGraterPriceThanException() {
        doReturn(Optional.empty()).when(bookRepository).lowestPriceBook(15);
        assertThrows(EmptyDataBase.class, () -> {
            bookService.getAllBooksGraterPriceThan(15);
        });
    }

    @Test
    void getAllBooksLowestPriceThanException() {
        doReturn(Optional.empty()).when(bookRepository).highPriceBook(15);
        assertThrows(EmptyDataBase.class, () -> {
            bookService.getAllBooksLowestPriceThan(15);
        });
    }


    @Test
    void getAllBooksLowestPriceThan() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            books.add(new Book().builder().stars(5L).title("Harry Potter").price(22).author("Flore Denis").build());
        }

        doReturn(Optional.of(books)).when(bookRepository).highPriceBook(20);
        assertEquals(5, bookService.getAllBooksLowestPriceThan(20).get().size());
    }

    @Test
    void getAllBestBooks() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            books.add(new Book().builder().stars(5L).title("Harry Potter").price(22).author("Flore Denis").build());
        }

        doReturn(Optional.of(books)).when(bookRepository).bestBooks();
        assertEquals(5, bookService.getAllBestBooks().get().size());
    }

    @Test
    void getAllBestBooksException() {
        doReturn(Optional.empty()).when(bookRepository).bestBooks();
        assertThrows(EmptyDataBase.class, () -> {
            bookService.getAllBestBooks();
        });
    }

    @Test
    void getAllBooksByStars() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            books.add(new Book().builder().stars(5L).title("Harry Potter").price(22).author("Flore Denis").build());
        }

        doReturn(Optional.of(books)).when(bookRepository).filterStarBook(5L);
        assertEquals(5, bookService.getAllBooksByStars(5L).get().size());
    }

    @Test
    void getAllBooksByStarsException() {


        doReturn(Optional.empty()).when(bookRepository).filterStarBook(5L);
       assertThrows(EmptyDataBase.class,()->{
           bookService.getAllBooksByStars(5L);
       });
    }
}
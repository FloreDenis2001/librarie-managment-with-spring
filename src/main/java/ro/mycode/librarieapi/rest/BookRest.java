package ro.mycode.librarieapi.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.librarieapi.model.Book;
import ro.mycode.librarieapi.service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@Slf4j
public class BookRest {

    private BookService bookService;

    public BookRest(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> bookList(){
        List<Book> bookList=bookService.getAllBooks();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @GetMapping("/greaterPrice/{price}")
    public ResponseEntity<List<Book>> bookListGraterPrice(@PathVariable double price){
        Optional<List<Book>> bookList=bookService.getAllBooksGraterPriceThan(price);
        return new ResponseEntity<>(bookList.get(),HttpStatus.OK);
    }

    @GetMapping("/lowerPrice/{price}")
    public ResponseEntity<List<Book>> bookListLowerPrice(@PathVariable double price){
        Optional<List<Book>> bookList=bookService.getAllBooksLowestPriceThan(price);
        return new ResponseEntity<>(bookList.get(),HttpStatus.OK);
    }

    @GetMapping("/bestbooks")
    public ResponseEntity<List<Book>> bookListBestBooks(){
        Optional<List<Book>> bookList=bookService.getAllBestBooks();
        return new ResponseEntity<>(bookList.get(),HttpStatus.OK);
    }

    @GetMapping("/bestbooks/{stars}")
    public ResponseEntity<List<Book>> bookListAllBooksByStars(@PathVariable Long stars){
        Optional<List<Book>> bookList=bookService.getAllBooksByStars(stars);
        return new ResponseEntity<>(bookList.get(),HttpStatus.OK);
    }


}

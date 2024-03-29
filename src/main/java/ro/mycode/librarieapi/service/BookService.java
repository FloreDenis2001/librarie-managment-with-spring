package ro.mycode.librarieapi.service;


import ro.mycode.librarieapi.exceptii.EmptyDataBase;
import ro.mycode.librarieapi.model.Book;
import ro.mycode.librarieapi.repository.BookRepo;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepo bookRepository;

    public BookService(BookRepo bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks(){
        List<Book> bookList=bookRepository.findAll();

        if(bookList.size()==0){
            throw new EmptyDataBase("Baza de date este goala !");
        }

        return bookList;
    }

    public Optional<List<Book>> getAllBooksGraterPriceThan(double priceMin){
        Optional<List<Book>> bookList=bookRepository.lowestPriceBook(priceMin);
        if(bookList.isEmpty()){
            throw new EmptyDataBase("Baza de date este goala!");
        }

        return bookList;
    }

    public Optional<List<Book>> getAllBooksLowestPriceThan(double priceMax){
        Optional<List<Book>> bookList=bookRepository.highPriceBook(priceMax);
        if(bookList.isEmpty()){
            throw new EmptyDataBase("Baza de date este goala !");
        }
        return bookList;
    }


    public Optional<List<Book>> getAllBestBooks(){
        Optional<List<Book>> bookList=bookRepository.bestBooks();
        if(bookList.isEmpty()){
            throw  new EmptyDataBase("Baza de date este goala");
        }

        return bookList;
    }


    public Optional<List<Book>> getAllBooksByStars(Long stars){
        Optional<List<Book>> bookList=bookRepository.filterStarBook(stars);
        if(bookList.isEmpty()){
            throw new EmptyDataBase("Baza de date este goala !");
        }

        return bookList;
    }


}

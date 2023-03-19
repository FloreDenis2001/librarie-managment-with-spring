package ro.mycode.librarieapi.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mycode.librarieapi.model.Carte;
import ro.mycode.librarieapi.service.CarteService;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class CarteResource {
    private CarteService carteService;

    public CarteResource(CarteService carteService) {
        this.carteService = carteService;
    }


    @GetMapping("library/books/all")
    public ResponseEntity<List<Carte>> getAllBooks(){
        List<Carte> carte=carteService.getAllBook();
        log.info("REST api for getting all  books");
        return new ResponseEntity<>(carte, HttpStatus.OK);
    }
}

package ro.mycode.librarieapi.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
;
import ro.mycode.librarieapi.dto.CreateCarteRequestDTO;
import ro.mycode.librarieapi.dto.CreateCarteResponseDTO;
import ro.mycode.librarieapi.model.Carte;
import ro.mycode.librarieapi.service.StudentService;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class StudentResource {

   private StudentService studentService;

    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("library/student/allbooks/{id}")
    public ResponseEntity<List<Carte>> getAllBookByStudent(@PathVariable Long id){
         log.info("REST api for getting all the students books with id {} ",id);
         List<Carte> bookList=studentService.getAllBooksById(id);
         return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @PostMapping("library/student/addBook")
    public ResponseEntity<CreateCarteResponseDTO> addCarte(@RequestBody CreateCarteRequestDTO c){
        log.info("REST api for adding book {}",c);
        this.studentService.addBook(c);
        CreateCarteResponseDTO createCarteResponseDTO =CreateCarteResponseDTO.builder().message("Added book").carte(c).build();
        return new ResponseEntity<>(createCarteResponseDTO,HttpStatus.OK);
    }
}

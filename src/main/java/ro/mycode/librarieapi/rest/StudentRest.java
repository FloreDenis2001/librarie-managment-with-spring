package ro.mycode.librarieapi.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
;
import ro.mycode.librarieapi.dto.CreateBookRequest;
import ro.mycode.librarieapi.dto.CreateBookResponse;
import ro.mycode.librarieapi.dto.StudentListResponse;
import ro.mycode.librarieapi.service.StudentService;

@RestController
@CrossOrigin
@Slf4j
public class StudentRest {

    private StudentService studentService;

    public StudentRest(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public ResponseEntity<StudentListResponse> studentList() {
        StudentListResponse studentListResponse = StudentListResponse.builder().studentList(studentService.getAllStudents()).message("All students").build();
        return new ResponseEntity<>(studentListResponse, HttpStatus.OK);
    }

    @PostMapping("/addBook")
    public ResponseEntity<CreateBookRequest> addBookToAStudent(@RequestBody CreateBookRequest createBookRequest) {
        studentService.addBook(createBookRequest);
        return new ResponseEntity<>(createBookRequest, HttpStatus.CREATED);
    }

    @DeleteMapping("/cancelBook")
    public ResponseEntity<CreateBookRequest> cancelBookToAStudent(@RequestBody CreateBookRequest createBookRequest){
        studentService.removeBook(createBookRequest);
        return new ResponseEntity<>(createBookRequest,HttpStatus.OK);
    }
}

package ro.mycode.librarieapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import ro.mycode.librarieapi.dto.CreateCarteRequestDTO;
import ro.mycode.librarieapi.exceptii.CarteDoesNotExist;
import ro.mycode.librarieapi.exceptii.StudentAlreadyExists;
import ro.mycode.librarieapi.exceptii.StudentDoesNotExist;
import ro.mycode.librarieapi.model.Carte;
import ro.mycode.librarieapi.model.Student;
import ro.mycode.librarieapi.repository.StudentRepo;

import javax.transaction.Transactional;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepo studentRepo;


    private ObjectMapper objectMapper;

    public StudentService(StudentRepo studentRepo,ObjectMapper objectMapper) {
        this.studentRepo = studentRepo;
        this.objectMapper=objectMapper;
    }

    public List<Student> getAllStudent() throws StudentDoesNotExist{
        if(studentRepo.findAll().size()!=0){
            return studentRepo.findAll();
        }else {
            throw new StudentDoesNotExist("Student's doesn't exist !");
        }
    }

    public void add(Student student) throws StudentAlreadyExists{
        Optional<Student> s =studentRepo.findById(student.getId());
        if(s.isEmpty()){
            studentRepo.save(student);
        }else {
            throw new StudentAlreadyExists("Student already exist ! ");
        }
    }

    @Transactional
    @Modifying
    public void addBook(CreateCarteRequestDTO createCarteRequestDTO) throws CarteDoesNotExist,StudentDoesNotExist{


        //Student
        Optional<Student> student=studentRepo.findById(createCarteRequestDTO.getIdStudent());


        if(student.isEmpty()){

            throw new StudentDoesNotExist("Student doesn't exist! ");
        }



//        Carte carte= objectMapper.convertValue(createCarteRequestDTO,Carte.class);

//        Carte carte=

        Carte carte=Carte.builder()
                .bookName(createCarteRequestDTO.getBookName())
                .createdAt(createCarteRequestDTO.getCreatedAt())
                .description(createCarteRequestDTO.getDescription()).build();

       if(student.get().vfExistsBook(carte)){
           throw  new CarteDoesNotExist("Book doesn't exist!");
       }

       student.get().addBook(carte);
       studentRepo.saveAndFlush(student.get());
    }

    @Transactional
    public List<Carte> getAllBooksById(Long id) throws StudentDoesNotExist{
        Optional<Student> student=studentRepo.findById(id);
        if(student.isEmpty()){
            throw new StudentDoesNotExist("Student doesn't exist!");
        }

        return student.get().getBookList();
    }

}

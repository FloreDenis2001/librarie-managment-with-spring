package ro.mycode.librarieapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.mycode.librarieapi.repository.StudentRepo;

@SpringBootApplication
public class LibrarieApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarieApiApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(StudentRepo studentRepo){

		return args -> {


//			Carte carte=Carte.builder().
//					bookName("testName").
//					createdAt(LocalDate.now()).
//					description("testasdsa").
//					build();


//			Student student=studentRepo.findById(1L).get();










		};
	}
}

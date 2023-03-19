package ro.mycode.librarieapi.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateCarteRequestDTO {


    @NotNull
    private Long idStudent;
   @NotEmpty
   @Size(min=3,message = "The book has to have minim 3 characters")
   private String bookName;

   @NotEmpty
    private LocalDate createdAt;

    @NotEmpty
    @Size(min = 2,message = "The description has to have minim 2 characters")
    private String description;


}

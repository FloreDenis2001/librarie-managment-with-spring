package ro.mycode.librarieapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateCarteResponseDTO {
    private String message;
    private CreateCarteRequestDTO carte;
}

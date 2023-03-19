package ro.mycode.librarieapi.exceptii;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CarteAlreadyExist extends RuntimeException{
    public CarteAlreadyExist(String message) {
        super(message);
    }
}

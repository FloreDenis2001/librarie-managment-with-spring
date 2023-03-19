package ro.mycode.librarieapi.exceptii;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyDataBase extends  RuntimeException{
    public EmptyDataBase(String message) {
        super(message);
    }
}

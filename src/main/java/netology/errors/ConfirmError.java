package netology.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ConfirmError extends RuntimeException{
    public ConfirmError(String msg) {
        super(msg);
    }
}

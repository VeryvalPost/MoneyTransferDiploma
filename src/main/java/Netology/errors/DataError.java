package Netology.errors;

import Netology.logger.LoggerClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataError extends RuntimeException{
    public DataError(String msg) {
        super(msg);
        LoggerClass.WriteLog(msg);
    }
}

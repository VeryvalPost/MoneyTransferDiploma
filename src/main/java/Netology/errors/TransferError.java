package Netology.errors;

import Netology.model.LoggerClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class TransferError extends RuntimeException{
    public TransferError(String msg) {
        super(msg);
        LoggerClass.WriteLog(msg);
    }
}

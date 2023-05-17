package Netology.errors;

import Netology.model.ErrorDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import Netology.logger.LoggerClass;

@RestControllerAdvice
public class ExeptionHandler {

    static LoggerClass log = new LoggerClass();

    @ExceptionHandler(DataError.class)
    public ErrorDTO handleInputData(DataError e) {
        log.WriteLog("Not corrected card data: " + e.getMessage());
        return new ErrorDTO("Not corrected card data: " + e.getMessage(), 400);
    }

    @ExceptionHandler(TransferError.class)
    public ErrorDTO handleErrorTransfer(TransferError e) {
        log.WriteLog("Transfer error: " + e.getMessage());
        return new ErrorDTO("Transfer error: " + e.getMessage(), 500);
    }

    @ExceptionHandler(ConfirmError.class)
    public ErrorDTO handleErrorConfirmation(ConfirmError e) {
        log.WriteLog("Confirmation error" + e.getMessage());
        return new ErrorDTO("Confirmation error", 500);
    }
}

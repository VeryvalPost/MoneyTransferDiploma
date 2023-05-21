package netology.errors;

import netology.model.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import netology.logger.LoggerClass;

@RestControllerAdvice
public class ExeptionHandler {

    static LoggerClass log = new LoggerClass();

    @ExceptionHandler(DataError.class)
    public ResponseEntity handleErrorData(DataError e) {
        log.WriteLog("Not corrected card data: " + e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorDTO("Not corrected card data",400));
    }

    @ExceptionHandler(TransferError.class)
    public ResponseEntity handleErrorTransfer(TransferError e) {
        log.WriteLog("Transfer error: " + e.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorDTO("Transfer error: ",500));
    }

    @ExceptionHandler(ConfirmError.class)
    public ResponseEntity handleErrorConfirmation(ConfirmError e) {
        log.WriteLog("Confirmation error" + e.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorDTO("Confirmation error: ",500));
    }
}

package netology.controller;

import netology.model.ConfirmationData;
import netology.logger.LoggerClass;
import netology.model.OperationID;
import netology.model.TransferData;
import netology.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TransferServController {
    private final CardService cardService;

    public TransferServController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<OperationID> transferToCard(@RequestBody TransferData transferData) {
        LoggerClass.WriteLog("New transfer:" + transferData.toString());
        return ResponseEntity.ok(cardService.transfer(transferData));
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<OperationID> confirmOperation(@RequestBody ConfirmationData confirmationData) {
        LoggerClass.WriteLog("New confirmation:" + confirmationData.toString());
        return ResponseEntity.ok(cardService.confirmation(confirmationData));
    }
}

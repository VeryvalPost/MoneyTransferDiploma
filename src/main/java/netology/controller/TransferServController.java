package netology.controller;

import netology.model.ConfirmationData;
import netology.logger.LoggerClass;
import netology.model.OperationID;
import netology.model.TransferData;
import netology.service.CardService;
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
    public OperationID transferToCard(@RequestBody TransferData transferData) {
        LoggerClass.WriteLog("New transfer");
        return cardService.transfer(transferData);
    }

    @PostMapping("/confirmOperation")
    public OperationID confirmOperation(@RequestBody ConfirmationData confirmationData) {
        LoggerClass.WriteLog("New confirmation");
        return cardService.confirmation(confirmationData);
    }


}

package Netology.controller;

import Netology.model.ConfirmationData;
import Netology.logger.LoggerClass;
import Netology.model.OperationID;
import Netology.model.TransferData;
import Netology.service.Service;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class Controller {
    Service service;

    public Controller(Service service) {
        this.service = service;
    }


    @PostMapping("/transfer")
    public OperationID transferToCard(@RequestBody TransferData transferData) {
        LoggerClass.WriteLog("New transfer");
        return service.transfer(transferData);
    }

    @PostMapping("/confirmOperation")
    public OperationID confirmOperation(@RequestBody ConfirmationData confirmationData) {
        LoggerClass.WriteLog("New confirmation");
        return service.confirmation(confirmationData);
    }



}

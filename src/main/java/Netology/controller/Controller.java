package Netology.controller;

import Netology.model.ConfirmationData;
import Netology.model.TransferData;
import Netology.service.Service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    Service service;

    public Controller(Service service) {
        this.service = service;
    }


    @PostMapping("/transfer")
    public void transferToCard(@RequestBody TransferData transferData) {}

    @PostMapping("/confirmOperation")
    public void confirmOperation(@RequestBody ConfirmationData confirmationData) {}



}

package Netology.service;

import Netology.model.*;
import Netology.repository.Repository;

@org.springframework.stereotype.Service
public class Service {

    Repository repository;
    Card cardTest1 = new Card("1111111111111111","03/27","1111",new Amount(5000,"RUB"));
    Card cardTest2 = new Card("2222222222222222","02/29","2222",new Amount(1000,"RUB"));



    public Service(Repository repository){
        this.repository = repository;
    }

    public OperationID transfer(TransferData transferData) {
        String cardFromNumber = transferData.getCardFromNumber();
        String cardFromTill = transferData.getCardFromValidTill();
        String cardFromCVV = transferData.getCardFromCVV();
        String cardToNumber = transferData.getCardToNumber();



        return new OperationID("null");
    }

    public void confirmation(ConfirmationData confirmationData){}

    public void checkBalance(){
    }

    public void checkCardData(){
    }



}

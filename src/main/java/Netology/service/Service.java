package Netology.service;

import Netology.model.*;
import Netology.repository.Repository;

import java.time.*;
import java.util.Calendar;

@org.springframework.stereotype.Service
public class Service {

    Repository repository;

    public Service(Repository repository){

        this.repository = repository;
        // заполнение тестовыми данными
        repository.addCardToRepo("1111111111111111","03/27","1111",5000,"RUB");
        repository.addCardToRepo("2222222222222222","02/29","2222",1000,"RUB");
    }

    public OperationID transfer(TransferData transferData) {
        String cardFromNumber = transferData.getCardFromNumber();
        String cardFromTill = transferData.getCardFromValidTill();
        String cardFromCVV = transferData.getCardFromCVV();
        String cardToNumber = transferData.getCardToNumber();
        Amount amount = transferData.getAmount();
        if ((checkCardData(cardFromNumber, cardFromTill, cardFromCVV))&&
                (checkBalance(cardFromNumber,amount))){
            //увеличиваем денежную сумму на карте на заданную величну
            Amount transferAmount = repository.getCard(cardToNumber).getBalance();
            transferAmount.setValue(transferAmount.getValue()+amount.getValue());
            return repository.addTransferToRepo(transferData, true);
        }
        return repository.addTransferToRepo(transferData, false);
    }

    public boolean confirmation(ConfirmationData confirmationData){
        if (repository.transferMap.containsKey(confirmationData.getOperationId())){
            repository.addConfirmationToRepo(confirmationData);
        }
        if (confirmationData.getCode().equals(confirmationData.getOperationId())){
            return true;
        } else return false;
    }

    public boolean checkBalance(String cardFromNumber, Amount amount){
        if ((repository.getCard(cardFromNumber).getBalance().getValue() > amount.getValue())&&
                (repository.getCard(cardFromNumber).getBalance().getCurrency() == amount.getCurrency())) return true;
        else return false;
    }

    public boolean checkCardData(String cardFromNumber, String cardFromTill, String cardFromCVV){
        // достаем из  репозитория значения, которые соответствуют проверяемой карте.
        String checkTillFrom = "00/00";
        String checkCardFromCVV = "000";
        for (Card element: repository.cards
             ) {
            if (element.getNumber().equals(cardFromNumber)){
                 checkTillFrom = element.getValidTill();
                 checkCardFromCVV = element.getCvv();
            }
        }


        String[] getDate = cardFromTill.split("/");
        int getMonth = Integer.parseInt("20" + getDate[0]);
        int getYear = Integer.parseInt(getDate[1]);

        String[] repDate = checkTillFrom.split("/");
        int repMonth = Integer.parseInt("20" + repDate[0]);
        int repYear = Integer.parseInt(repDate[1]);

        // проверяем соответствие данных на карте данным из репозитория
        boolean testCardYear = false;
        if ((repYear==getYear)&&(repMonth==getMonth)) return testCardYear=true;

        int curYear = Calendar.YEAR;
        int curMonth = Calendar.MONTH;

        // параллельно проверяем соответствие данных на карте текущей дате.
        boolean testCurrentYear = false;
        if ((curYear<=getYear)&&(curMonth<=getMonth)) return testCurrentYear=true;

        // параллельно проверяем соответствие данных CVV.
        boolean testCVV = false;
        if (checkCardFromCVV.equals(cardFromCVV)) return testCVV=true;

        // если проходит все тесты, то финальное значение true
        if (((testCardYear)&&(testCurrentYear))&&(testCVV)) return true;
        else return false;
    }



}

package Netology.service;

import Netology.errors.ConfirmError;
import Netology.errors.DataError;
import Netology.errors.TransferError;
import Netology.logger.LoggerClass;
import Netology.model.*;
import Netology.repository.Repository;

import java.util.Calendar;

@org.springframework.stereotype.Service
public class Service {

    Repository repository;
    Card cardTest1 = new Card("1111111111111111", "03/27", "111", new Amount(5000, "RUB"));
    Card cardTest2 = new Card("2222222222222222", "02/29", "222", new Amount(1000, "RUB"));
    static LoggerClass log = new LoggerClass();

    public Service(Repository repository) {

        this.repository = repository;
        // заполнение тестовыми данными
        repository.addCardToRepo("1111111111111111", "03/27", "111", 5000, "RUR");
        repository.addCardToRepo("2222222222222222", "02/29", "222", 1000, "RUR");
    }

    public OperationID transfer(TransferData transferData) {
        String cardFromNumber = transferData.getCardFromNumber();
        String cardFromTill = transferData.getCardFromValidTill();
        String cardFromCVV = transferData.getCardFromCVV();
        String cardToNumber = transferData.getCardToNumber();
        Amount amount = transferData.getAmount();

        if ((repository.checkBalance(cardFromNumber, amount))
                && (repository.checkCardExist(cardFromNumber))
                && (repository.checkCardExist(cardToNumber))
                && (repository.checkCardData(cardFromNumber, cardFromTill, cardFromCVV))) {
            //увеличиваем денежную сумму на карте на заданную величну
            Amount transferAmount = repository.getCard(cardToNumber).getBalance();
            transferAmount.setValue(transferAmount.getValue() + amount.getValue());
            //уменьшаем баланс исходной карты
            Amount decrease = repository.getCard(cardFromNumber).getBalance();
            int percent = (int)(amount.getValue()*0.01);
            decrease.setValue(decrease.getValue() - amount.getValue()-percent);
            log.WriteLog("\n" + "Balance " + cardToNumber + " increased by " +
                    amount.getValue() + amount.getCurrency() + "\n" +
                    "From " + cardFromNumber + "\n" +
                    "Current balance: " + repository.getCard(cardFromNumber).getBalance() + "\n" +
                    "Commission: " + percent);

        } else throw new TransferError("Transfer Error. Not enough money");


        return repository.addTransferToRepo(transferData);
    }

    public OperationID confirmation(ConfirmationData confirmationData) {

        boolean confSuccess = false;

        // на стороне FRONT организована другая поцедура проверки. В данный момент приложение высылает по умолчанию код "0000"
        /*
        for (OperationID elem : repository.transferMap.keySet()
        ) {
            if (elem.getId().equals(confirmationData.getOperationId())) {
                confSuccess = true;
                log.WriteLog("Confirm Success");
                return repository.addConfirmationToRepo(confirmationData);
            }
        }
        */

        // изменил проверк под конкретную задачу.

        if (confirmationData.getCode().equals("0000")) {
            confSuccess = true;
            log.WriteLog("Confirm Success");
            return repository.addConfirmationToRepo(confirmationData);
        }

        throw new ConfirmError("Confirm Error. Confirmation not exist");
    }




}

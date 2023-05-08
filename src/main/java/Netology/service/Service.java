package Netology.service;

import Netology.errors.ConfirmError;
import Netology.errors.DataError;
import Netology.errors.TransferError;
import Netology.model.*;
import Netology.repository.Repository;
import com.sun.jdi.connect.TransportTimeoutException;

import java.time.*;
import java.util.Calendar;

@org.springframework.stereotype.Service
public class Service {

    Repository repository;
    Card cardTest1 = new Card("1111111111111111", "03/27", "111", new Amount(5000, "RUB"));
    Card cardTest2 = new Card("2222222222222222", "02/29", "222", new Amount(1000, "RUB"));


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


        if ((checkBalance(cardFromNumber, amount))
                && (checkCardExist(cardFromNumber))
                && (checkCardExist(cardToNumber))
                && (checkCardData(cardFromNumber, cardFromTill, cardFromCVV))) {
            //увеличиваем денежную сумму на карте на заданную величну
            Amount transferAmount = repository.getCard(cardToNumber).getBalance();
            transferAmount.setValue(transferAmount.getValue() + amount.getValue());
            //уменьшаем баланс исходной карты
            Amount decrease = repository.getCard(cardFromNumber).getBalance();
            int percent = (int)(amount.getValue()*0.01);
            decrease.setValue(decrease.getValue() - amount.getValue()-percent);
            LoggerClass.WriteLog("\n" + "Balance " + cardToNumber + " increased by " +
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
                LoggerClass.WriteLog("Confirm Success");
                return repository.addConfirmationToRepo(confirmationData);
            }
        }
        */

        // изменил проверк под конкретную задачу.

        if (confirmationData.getCode().equals("0000")) {
            confSuccess = true;
            return repository.addConfirmationToRepo(confirmationData);
        }

        throw new ConfirmError("Confirm Error. Confirmation not exist");
    }

    public boolean checkBalance(String cardFromNumber, Amount amount) {


        if (((repository.getCard(cardFromNumber).getBalance().getValue() - amount.getValue()) > 0) &&
                (repository.getCard(cardFromNumber).getBalance().getCurrency().equals(amount.getCurrency())))
            return true;
        else return false;
    }

    public boolean checkCardData(String cardFromNumber, String cardFromTill, String cardFromCVV) {
        // достаем из  репозитория значения, которые соответствуют проверяемой карте.
        String checkTillFrom = "00/00";
        String checkCardFromCVV = "000";
        for (Card element : repository.cards
        ) {
            if (element.getNumber().equals(cardFromNumber)) {
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
        if ((repYear == getYear) && (repMonth == getMonth)) testCardYear = true;

        int curYear = Calendar.YEAR;
        int curMonth = Calendar.MONTH;

        // параллельно проверяем соответствие данных на карте текущей дате.
        boolean testCurrentYear = false;
        if ((curYear <= getYear) && (curMonth <= getMonth)) testCurrentYear = true;

        // параллельно проверяем соответствие данных CVV.
        boolean testCVV = false;
        if (checkCardFromCVV.equals(cardFromCVV)) testCVV = true;

        // если проходит все тесты, то финальное значение true
        if (((testCardYear) && (testCurrentYear)) && (testCVV)) return true;
        else throw new DataError("Card - " + cardFromNumber + " not corrected");
    }


    public boolean checkCardExist(String cardToNumber) {

        for (Card element : repository.cards
        ) {
            if (element.getNumber().equals(cardToNumber)) {
                return true;
            }
        }
        return false;
    }


}

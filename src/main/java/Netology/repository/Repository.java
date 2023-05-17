package Netology.repository;

import Netology.errors.DataError;
import Netology.logger.LoggerClass;
import Netology.model.*;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


@org.springframework.stereotype.Repository
public class Repository {
    public Map<OperationID, TransferData> transferMap = new ConcurrentHashMap<>();
    public Map<OperationID, ConfirmationData> confirmationMap = new ConcurrentHashMap<>();
    public CopyOnWriteArrayList<Card> cards = new CopyOnWriteArrayList<>();
    private AtomicInteger id = new AtomicInteger(0);
    static LoggerClass log = new LoggerClass();

    public OperationID addTransferToRepo(TransferData transferData){
        OperationID newOperation = new OperationID(String.valueOf(id.incrementAndGet()));
        transferMap.put((newOperation),transferData);
        log.WriteLog("Transfer ID - " + newOperation.getId());
        return newOperation;
    }

    public OperationID addConfirmationToRepo(ConfirmationData confirmationData){
        OperationID newOperation = new OperationID(String.valueOf(id.incrementAndGet()));
        confirmationMap.put(newOperation,confirmationData);
        log.WriteLog("Confirmation ID - " + newOperation.getId());
        return newOperation;
    }

    public void addCardToRepo(String number, String validTill, String cvv, int value, String currency){
        Amount balance = new Amount(value,currency);
        Card newCard = new Card(number, validTill, cvv, balance);
       log.WriteLog("New card added to repository" + newCard.toString());
        cards.add(newCard);
    }

    public Card getCard(String cardNumber){
        for (Card element: cards
             ) {
            if (element.getNumber().equals(cardNumber)){
                return element;
            }
        }
        throw new DataError("Card data error");



    }

    public Card createNewCard(String number){
        Card newCard = new Card(number, "00/00", "000",new Amount(0,"EMPTY"));
        cards.add(newCard);
        log.WriteLog("Added new EMPTY card");

        return newCard;
    }


    public boolean checkCardExist(String cardToNumber) {

        for (Card element : cards
        ) {
            if (element.getNumber().equals(cardToNumber)) {
                return true;
            }
        }
        return false;
    }


    public boolean checkBalance(String cardFromNumber, Amount amount) {


        if (((this.getCard(cardFromNumber).getBalance().getValue() - amount.getValue()) > 0) &&
                (this.getCard(cardFromNumber).getBalance().getCurrency().equals(amount.getCurrency())))
            return true;
        else return false;
    }

    public boolean checkCardData(String cardFromNumber, String cardFromTill, String cardFromCVV) {
        // достаем из  репозитория значения, которые соответствуют проверяемой карте.
        String checkTillFrom = "00/00";
        String checkCardFromCVV = "000";
        for (Card element : cards
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

}

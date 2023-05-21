package netology.repository;

import netology.errors.DataError;
import netology.logger.LoggerClass;
import netology.model.*;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class OperationRepository {
    public Map<OperationID, TransferData> transferMap = new ConcurrentHashMap<>();
    public Map<OperationID, ConfirmationData> confirmationMap = new ConcurrentHashMap<>();
    public Map<String, Card> cards = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);
    static LoggerClass log = new LoggerClass();

    public OperationID addTransferToRepo(TransferData transferData) {
        OperationID newOperation = new OperationID(String.valueOf(id.incrementAndGet()));
        transferMap.put((newOperation), transferData);
        log.WriteLog("Transfer ID - " + newOperation.getId());
        return newOperation;
    }

    public OperationID addConfirmationToRepo(ConfirmationData confirmationData) {
        OperationID newOperation = new OperationID(String.valueOf(id.incrementAndGet()));
        confirmationMap.put(newOperation, confirmationData);
        log.WriteLog("Confirmation ID - " + newOperation.getId());
        return newOperation;
    }

    public void addCardToRepo(String number, String validTill, String cvv, int value, String currency) {
        Amount balance = new Amount(value, currency);
        Card newCard = new Card(number, validTill, cvv, balance);
        log.WriteLog("New card added to repository" + newCard.toString());
        cards.put(newCard.getNumber(), newCard);
    }

    public Card getCard(String cardNumber) {

        for (Map.Entry<String, Card> element : cards.entrySet()
        ) {
            if (element.getKey().equals(cardNumber)) {
                return element.getValue();
            }
        }
        throw new DataError("Card" + cardNumber + " not found");


    }

    public Card createNewCard(String number) {
        Card newCard = new Card(number, "00/00", "000", new Amount(0, "EMPTY"));
        cards.put(number, newCard);
        log.WriteLog("Added new EMPTY card");

        return newCard;
    }

}
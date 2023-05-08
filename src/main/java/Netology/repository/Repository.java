package Netology.repository;


import Netology.errors.DataError;
import Netology.model.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@org.springframework.stereotype.Repository
public class Repository {
    public Map<OperationID, TransferData> transferMap = new ConcurrentHashMap<>();
    public Map<OperationID, ConfirmationData> confirmationMap = new ConcurrentHashMap<>();
    public ArrayList<Card> cards = new ArrayList<>();
    private int id=0;



    public OperationID addTransferToRepo(TransferData transferData){
        OperationID newOperation = new OperationID(String.valueOf(id++));
        transferMap.put((newOperation),transferData);
        LoggerClass.WriteLog("Transfer ID - " + newOperation.getId());
        return newOperation;
    }

    public OperationID addConfirmationToRepo(ConfirmationData confirmationData){
        OperationID newOperation = new OperationID(String.valueOf(id++));
        confirmationMap.put(newOperation,confirmationData);
        LoggerClass.WriteLog("Confirmation ID - " + newOperation.getId());
        return newOperation;
    }

    public void addCardToRepo(String number, String validTill, String cvv, int value, String currency){
        Amount balance = new Amount(value,currency);
        Card newCard = new Card(number, validTill, cvv, balance);
        LoggerClass.WriteLog("New card added to repository" + newCard.toString());
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
        LoggerClass.WriteLog("Added new EMPTY card");

        return newCard;
    }


}

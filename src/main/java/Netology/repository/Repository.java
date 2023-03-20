package Netology.repository;


import Netology.model.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.stereotype.Repository
public class Repository {
    Map<OperationID, TransferData> transferMap = new ConcurrentHashMap<>();
    Map<OperationID, ConfirmationData> confirmationMap = new ConcurrentHashMap<>();
    ArrayList<Card> cards = new ArrayList<>();
    AtomicInteger id;

    public void addTransferToRepo(TransferData transferData){
        transferMap.put(new OperationID(String.valueOf(id.incrementAndGet())),transferData);
    }

    public void addConfirmationToRepo(ConfirmationData confirmationData){
        confirmationMap.put(new OperationID(String.valueOf(id.incrementAndGet())),confirmationData);
    }

    public void addCardToRepo(String number, String validTill, String cvv, int value, String currency){
        Amount balance = new Amount(value,currency);
        Card newCard = new Card(number, validTill, cvv, balance);
        cards.add(newCard);
    }

}

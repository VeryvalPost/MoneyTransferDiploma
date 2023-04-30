package Netology.repository;


import Netology.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.stereotype.Repository
public class Repository {
    public Map<OperationID, TransferData> transferMap = new ConcurrentHashMap<>();
    public Map<OperationID, ConfirmationData> confirmationMap = new ConcurrentHashMap<>();
    public ArrayList<Card> cards = new ArrayList<>();
    AtomicInteger id;



    public OperationID addTransferToRepo(TransferData transferData, Boolean success){
        OperationID newOperation = new OperationID(String.valueOf(id.incrementAndGet()),success);
        transferMap.put((newOperation),transferData);
        return newOperation;
    }

    public void addConfirmationToRepo(ConfirmationData confirmationData){
        confirmationMap.put(new OperationID(String.valueOf(id.incrementAndGet()),true),confirmationData);
        confirmationData.setCode(confirmationData.getOperationId());
    }

    public void addCardToRepo(String number, String validTill, String cvv, int value, String currency){
        Amount balance = new Amount(value,currency);
        Card newCard = new Card(number, validTill, cvv, balance);
        cards.add(newCard);
    }

    public Card getCard(String cardNumber){
        for (Card element: cards
             ) {
            if (element.getNumber().equals(cardNumber)){
                return element;
            }
        }
        return createNewCard(cardNumber);
    }

    public Card createNewCard(String number){
        Card newCard = new Card(number, "00/00", "000",new Amount(0,"EMPTY"));
        cards.add(newCard);
        return newCard;
    }


}

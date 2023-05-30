package netology.service;

import netology.errors.DataError;
import netology.model.Amount;
import netology.model.Card;
import netology.repository.OperationRepository;
import java.util.Map;

public class ValidationService {

    private OperationRepository repository;

    public ValidationService(OperationRepository repository) {
        this.repository = repository;
    }

    public boolean checkCardExist(String cardNumber, OperationRepository repository) {

        for (Map.Entry<String, Card> element : repository.cards.entrySet()
        ) {
            if (element.getKey().equals(cardNumber)) {
                return true;
            }
        }
        throw new DataError("Card doesn't exist:" + cardNumber);
    }

    public boolean checkBalance(String cardFromNumber, Amount amount, Card card) {

        if (((card.getBalance().getValue() - amount.getValue()) > 0) &&
                (card.getBalance().getCurrency().equals(amount.getCurrency())))
            return true;
        else throw new DataError("Not enough money. Card:" + cardFromNumber);
    }
}



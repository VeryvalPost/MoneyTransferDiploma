package netology.service;

import netology.errors.ConfirmError;
import netology.errors.TransferError;
import netology.logger.LoggerClass;
import netology.model.*;
import netology.repository.OperationRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final OperationRepository repository;
    static LoggerClass log = new LoggerClass();

    public CardService(OperationRepository repository) {

        this.repository = repository;
        // заполнение тестовыми данными
        repository.addCardToRepo("1111111111111111", "03/27", "111", 5000, "RUR");
        repository.addCardToRepo("2222222222222222", "02/29", "222", 1000, "RUR");
    }

    public OperationID transfer(TransferData transferData) {
        ValidationService validator = new ValidationService(repository);
        String cardFromNumber = transferData.getCardFromNumber();
        String cardFromTill = transferData.getCardFromValidTill();
        String cardFromCVV = transferData.getCardFromCVV();
        String cardToNumber = transferData.getCardToNumber();
        Amount amount = transferData.getAmount();

        if ((validator.checkBalance(cardFromNumber, amount, repository.getCard(cardFromNumber)))
                && (validator.checkCardExist(cardFromNumber, repository))
                && (validator.checkCardExist(cardToNumber, repository))
        ) {
            //увеличиваем денежную сумму на карте на заданную величну
            Amount transferAmount = repository.getCard(cardToNumber).getBalance();
            transferAmount.setValue(transferAmount.getValue() + amount.getValue());
            //уменьшаем баланс исходной карты
            Amount decrease = repository.getCard(cardFromNumber).getBalance();
            int percent = (int) (amount.getValue() * 0.01);
            decrease.setValue(decrease.getValue() - amount.getValue() - percent);
            log.WriteLog("\n" + "Balance " + cardToNumber + " increased by " +
                    amount.getValue() + amount.getCurrency() + "\n" +
                    "From " + cardFromNumber + "\n" +
                    "Current balance: " + repository.getCard(cardFromNumber).getBalance() + "\n" +
                    "Commission: " + percent);

        } else throw new TransferError("Transfer not correct.");
        return repository.addTransferToRepo(transferData);
    }

    public OperationID confirmation(ConfirmationData confirmationData) {

        boolean confSuccess = false;

        if (confirmationData.getCode().equals("0000")) {
            confSuccess = true;
            log.WriteLog("Confirm Success");
            return repository.addConfirmationToRepo(confirmationData);
        }

        throw new ConfirmError("Confirm Error. Confirmation not exist");
    }
}

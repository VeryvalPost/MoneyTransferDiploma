package Netology.repository;

import Netology.errors.DataError;
import Netology.logger.LoggerClass;
import Netology.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;


public class RepositoryTest {

    @Mock
    public LoggerClass logger;

    @InjectMocks
    public Repository repository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new Repository();
    }
    @Test
    public void testAddTransferToRepo() {

        TransferData transferData = new TransferData("1111111111111111","03/27",
                "111","22222222222222222",new Amount(5000,"RUR"));
        OperationID operationID = repository.addTransferToRepo(transferData);

        assertNotNull(operationID);
        assertTrue(repository.transferMap.containsKey(operationID));
        assertSame(transferData, repository.transferMap.get(operationID));
    }

    @Test
    public void testAddConfirmationToRepo() {
        ConfirmationData confirmationData = new ConfirmationData("1","0000");
        OperationID operationID = repository.addConfirmationToRepo(confirmationData);

        assertNotNull(operationID);
        assertTrue(repository.confirmationMap.containsKey(operationID));
        assertSame(confirmationData, repository.confirmationMap.get(operationID));
    }

    @Test
    public void testAddCardToRepo() {

        String number = "1234567890123456";
        String validTill = "12/25";
        String cvv = "123";
        int value = 1000;
        String currency = "RUR";

        repository.addCardToRepo(number, validTill, cvv, value, currency);

        assertFalse(repository.cards.isEmpty());
        assertEquals(1, repository.cards.size());

        Card card = repository.cards.get(0);
        assertEquals(number, card.getNumber());
        assertEquals(validTill, card.getValidTill());
        assertEquals(cvv, card.getCvv());
        assertEquals(value, card.getBalance().getValue());
        assertEquals(currency, card.getBalance().getCurrency());

    }

    @Test
    public void testGetCard() {
        String number = "111111111111111111";
        String validTill = "03/27";
        String cvv = "111";
        int value = 1000;
        String currency = "RUR";

        repository.addCardToRepo(number, validTill, cvv, value, currency);

        Card card = repository.getCard(number);

        assertEquals(number, card.getNumber());
        assertEquals(validTill, card.getValidTill());
        assertEquals(cvv, card.getCvv());
        assertEquals(value, card.getBalance().getValue());
        assertEquals(currency, card.getBalance().getCurrency());

        assertThrows(DataError.class, () -> repository.getCard("Data not correct"));
    }

    @Test
    public void testCreateNewCard() {
        String number = "1234567890123456";

        Card newCard = repository.createNewCard(number);

        assertNotNull(newCard);
        assertFalse(repository.cards.isEmpty());
        assertEquals(1, repository.cards.size());

        assertEquals(number, newCard.getNumber());
        assertEquals("00/00", newCard.getValidTill());
        assertEquals("000", newCard.getCvv());
        assertEquals(0, newCard.getBalance().getValue());
        assertEquals("EMPTY", newCard.getBalance().getCurrency());

    }
}

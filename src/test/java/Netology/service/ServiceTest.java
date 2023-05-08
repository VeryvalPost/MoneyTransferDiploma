package Netology.service;

import Netology.model.*;
import Netology.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ServiceTest {
    @Mock
    Repository repository;

    @Mock
    TransferData transferData;

    @InjectMocks
    Service service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new Service(repository);
    }

    @Test
    public void testTransfer() {

        // Mocking TransferData object
        TransferData transferData = mock(TransferData.class);
        when(transferData.getCardFromNumber()).thenReturn("1111111111111111");
        when(transferData.getCardFromValidTill()).thenReturn("03/27");
        when(transferData.getCardFromCVV()).thenReturn("111");
        when(transferData.getCardToNumber()).thenReturn("2222222222222222");
        when(transferData.getAmount()).thenReturn(new Amount(1000, "RUB"));

        // Mocking Card object
        Card cardFrom = mock(Card.class);
        when(cardFrom.getNumber()).thenReturn("1111111111111111");
        when(cardFrom.getValidTill()).thenReturn("03/27");
        when(cardFrom.getCvv()).thenReturn("111");
        when(cardFrom.getBalance()).thenReturn(new Amount(6000, "RUB"));

        Card cardTo = mock(Card.class);
        when(cardTo.getNumber()).thenReturn("2222222222222222");
        when(cardTo.getValidTill()).thenReturn("02/29");
        when(cardTo.getCvv()).thenReturn("222");
        when(cardTo.getBalance()).thenReturn(new Amount(1000, "RUB"));

        // Setting up the mocks for the repository
        when(repository.getCard("1111111111111111")).thenReturn(cardFrom);
        when(repository.getCard("2222222222222222")).thenReturn(cardTo);
        when(repository.addTransferToRepo(any(TransferData.class))).thenReturn(new OperationID("1"));

        // Testing the transfer method
        OperationID operationID = service.transfer(transferData);
        assertNotNull(operationID);
        assertEquals(operationID.getClass(), OperationID.class);
       // verify(repository, times(2)).getCard(anyString());
       // verify(repository, times(1)).addTransferToRepo(any(TransferData.class));
    }

    @Test
    public void testConfirmation() {
        // Mocking ConfirmationData object
        ConfirmationData confirmationData = mock(ConfirmationData.class);
        when(confirmationData.getOperationId()).thenReturn("1");
        when(confirmationData.getCode()).thenReturn("0000");

        // Setting up the mocks for the repository
        when(repository.addConfirmationToRepo(any(ConfirmationData.class))).thenReturn(new OperationID("1"));

        // Testing the confirmation method
        OperationID operationID = service.confirmation(confirmationData);
        assertNotNull(operationID);
        assertEquals(operationID.getClass(), OperationID.class);
        verify(repository, times(1)).addConfirmationToRepo(any(ConfirmationData.class));
    }


}
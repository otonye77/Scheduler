package com.peerless.peerless_bank.Controller;

import com.peerless.peerless_bank.Entities.Transfer;
import com.peerless.peerless_bank.Service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransferControllerTest {

    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferController transferController;

    private Transfer transfer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transfer = new Transfer();
        transfer.setTransferId(1L);
        transfer.setSenderAccountId(1L);
        transfer.setRecipientAccountId(2L);
        transfer.setTransferAmount(new BigDecimal("1000.00"));
        transfer.setTransferDate(LocalDateTime.now());
    }

    @Test
    void testScheduleTransfer() {
        doNothing().when(transferService).scheduleTransfer(any(Transfer.class));

        ResponseEntity<String> response = transferController.scheduleTransfer(transfer);

        assertEquals(201, response.getStatusCodeValue());
        verify(transferService, times(1)).scheduleTransfer(transfer);
    }

    @Test
    void testScheduleTransferBadRequest() {
        doThrow(new IllegalArgumentException("Invalid transfer")).when(transferService).scheduleTransfer(any(Transfer.class));

        ResponseEntity<String> response = transferController.scheduleTransfer(transfer);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetScheduledTransfers() {
        when(transferService.getScheduledTransfers(1L)).thenReturn(List.of(transfer));

        ResponseEntity<List<Transfer>> response = transferController.getScheduledTransfers(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testCancelScheduledTransfer() {
        doNothing().when(transferService).cancelScheduledTransfer(1L);

        ResponseEntity<String> response = transferController.cancelScheduledTransfer(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(transferService, times(1)).cancelScheduledTransfer(1L);
    }

    @Test
    void testCancelScheduledTransferBadRequest() {
        doThrow(new IllegalArgumentException("Transfer not found")).when(transferService).cancelScheduledTransfer(1L);

        ResponseEntity<String> response = transferController.cancelScheduledTransfer(1L);

        assertEquals(400, response.getStatusCodeValue());
    }
}
